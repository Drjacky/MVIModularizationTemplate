package app.web.drjackycv.core.designsystem

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import app.web.drjackycv.common.exceptions.ReactiveClickException
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

fun View?.gone() {
    this?.let {
        visibility = View.GONE
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun ImageView.load(
    url: String?,
    @DrawableRes placeholderRes: Int,
    isCircular: Boolean = false,
    action: ((Drawable) -> Unit),
) {
    val safePlaceholderDrawable = AppCompatResources.getDrawable(context, placeholderRes)
    val requestOptions = RequestOptions().apply {
        placeholder(safePlaceholderDrawable)
        error(safePlaceholderDrawable)
        if (isCircular) circleCrop()
    }
    val glideRequest = GlideApp
        .with(context)
        .setDefaultRequestOptions(requestOptions)
        .load(url)
        .dontAnimate()

    glideRequest
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean,
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>,
                dataSource: DataSource,
                isFirstResource: Boolean,
            ): Boolean {
                action(resource)
                return false
            }
        })

    glideRequest.into(this)
}

fun View.setOnReactiveClickListener(windowDuration: Long = 500, action: (() -> Unit)?): Disposable =
    this.clicks()
        .throttleFirst(windowDuration, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            action?.invoke()
        }, { throwable ->
            throw ReactiveClickException(
                msg = throwable.message ?: "Unknown Reactive Click Exception!",
                cause = throwable.cause,
                stack = throwable.stackTrace
            )
        })

fun String.titleCase(): String = replaceFirstChar { it.uppercase() }
