package app.web.drjackycv.core.designsystem

import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}

class FragmentViewBindingDelegate<T : ViewBinding>(
    val fragment: Fragment,
    val viewBindingFactory: (Fragment) -> T,
    val cleanUp: ((T?) -> Unit)?,
) : ReadOnlyProperty<Fragment, T> {

    // A backing property to hold our value
    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            val viewLifecycleOwnerObserver = Observer<LifecycleOwner?> { owner ->
                if (owner == null) {
                    cleanUp?.invoke(binding)
                    binding = null
                }
            }

            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever(
                    viewLifecycleOwnerObserver
                )
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver(
                    viewLifecycleOwnerObserver
                )
            }
        })
    }

    override fun getValue(
        thisRef: Fragment,
        property: KProperty<*>,
    ): T {
        val binding = binding
        if (binding != null) {
            return binding
        }

        val lifecycle = thisRef.viewLifecycleOwner.lifecycle
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED).not()) {
            throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")
        }

        return viewBindingFactory(thisRef).also { this.binding = it }
    }
}

inline fun <T : ViewBinding> Fragment.viewBinding(
    crossinline viewBindingFactory: (View) -> T,
    noinline cleanUp: ((T?) -> Unit)? = null,
): FragmentViewBindingDelegate<T> =
    FragmentViewBindingDelegate(this, { f -> viewBindingFactory(f.requireView()) }, cleanUp)

inline fun <T : ViewBinding> Fragment.viewInflateBinding(
    crossinline bindingInflater: (LayoutInflater) -> T,
    noinline cleanUp: ((T?) -> Unit)? = null,
): FragmentViewBindingDelegate<T> =
    FragmentViewBindingDelegate(this, { f -> bindingInflater(f.layoutInflater) }, cleanUp)

inline fun <T : ViewBinding> AppCompatActivity.viewInflateBinding(
    crossinline bindingInflater: (LayoutInflater) -> T,
) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

fun <T> allowReads(block: () -> T): T {
    val oldPolicy = StrictMode.allowThreadDiskReads()
    try {
        return block()
    } finally {
        StrictMode.setThreadPolicy(oldPolicy)
    }
}

fun <T> allowWrites(block: () -> T): T {
    val oldPolicy = StrictMode.allowThreadDiskWrites()
    try {
        return block()
    } finally {
        StrictMode.setThreadPolicy(oldPolicy)
    }
}

inline fun <T> Flow<T>.collectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    crossinline action: suspend (T) -> Unit,
) = owner.lifecycleScope.launch(coroutineContext) {
    owner.lifecycle.repeatOnLifecycle(minActiveState) {
        collect {
            action(it)
        }
    }
}