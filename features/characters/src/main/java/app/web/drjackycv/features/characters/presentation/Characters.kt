package app.web.drjackycv.features.characters.presentation

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import app.web.drjackycv.common.exceptions.Failure
import app.web.drjackycv.common.models.Character
import app.web.drjackycv.core.designsystem.gone
import app.web.drjackycv.core.designsystem.invisible
import app.web.drjackycv.core.designsystem.viewBinding
import app.web.drjackycv.core.designsystem.visible
import app.web.drjackycv.features.characters.R
import app.web.drjackycv.features.characters.databinding.CharactersBinding
import com.google.android.material.transition.platform.Hold
import com.google.android.material.transition.platform.MaterialElevationScale

class Characters : Fragment(R.layout.characters) {

    private val binding by viewBinding(CharactersBinding::bind) {
        cleanUp(it)
    }
    private val charactersAdapter: CharactersAdapter by lazy {
        CharactersAdapter {
            //no-op
        }
    }

    private fun cleanUp(binding: CharactersBinding?) {
        binding?.rvCharactersList?.adapter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.inclItemError.itemErrorContainer.gone()
        binding.rvCharactersList.adapter =
            charactersAdapter.withLoadStateFooter(LoadingStateAdapter())
        charactersAdapter.addLoadStateListener { adapterLoadingErrorHandling(it) }
        charactersAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        //productListRecyclerView.itemAnimator = null
        postponeEnterTransition()
        view?.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun setupUI() {
        /*charactersListViewModel.run {
            charactersList.collectIn(viewLifecycleOwner) {
                addCharactersList(it)
            }

            failure.collectIn(viewLifecycleOwner) {
                handleFailure(it)
            }
        }*/
    }

    private fun addCharactersList(charactersList: PagingData<Character>) {
        binding.rvCharactersList.visible()
        charactersAdapter.submitData(lifecycle, charactersList)
    }

    private fun loadingUI(isLoading: Boolean) {
        binding.inclItemError.itemErrorContainer.gone()
        if (isLoading) {
            binding.inclItemLoading.itemLoadingContainer.visible()
        } else {
            binding.inclItemLoading.itemLoadingContainer.gone()
        }
    }

    private fun handleFailure(failure: Failure) {
        binding.rvCharactersList.gone()
        binding.inclItemError.itemErrorContainer.visible()
        when (failure) {
            is Failure.NoInternet, is Failure.Api, is Failure.Timeout -> {
                setupErrorItem(failure)
            }
            is Failure.Unknown -> {
                setupErrorItem(failure)
            }
            else -> {
                binding.inclItemError.itemErrorMessage.text = failure.message
                binding.inclItemError.itemErrorRetryBtn.invisible()
            }
        }
    }

    private fun navigateToCharacterDetail(character: Character, view: View) {
        val extras = FragmentNavigatorExtras(
            view to character.id
        )
        exitTransition = Hold().apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.motion_duration_small).toLong()
        }

        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://app.web.drjackycv/character-detail".toUri())
            .build()
        findNavController().navigate(request = request, navOptions = null, navigatorExtras = extras)
    }

    private fun adapterLoadingErrorHandling(combinedLoadStates: CombinedLoadStates) {
        if (combinedLoadStates.refresh is LoadState.Loading) {
            loadingUI(true)
        } else {
            loadingUI(false)
            val error = when {
                combinedLoadStates.prepend is LoadState.Error -> combinedLoadStates.prepend as LoadState.Error
                combinedLoadStates.source.prepend is LoadState.Error -> combinedLoadStates.prepend as LoadState.Error
                combinedLoadStates.append is LoadState.Error -> combinedLoadStates.append as LoadState.Error
                combinedLoadStates.source.append is LoadState.Error -> combinedLoadStates.append as LoadState.Error
                combinedLoadStates.refresh is LoadState.Error -> combinedLoadStates.refresh as LoadState.Error
                else -> null
            }
            error?.run {
                //charactersListViewModel.handleFailure(this.error) { retryFetchData() }
            }
        }
    }

    private fun retryFetchData() {
        binding.rvCharactersList.visible()
        charactersAdapter.retry()
    }

    private fun setupErrorItem(failure: Failure) {
        binding.inclItemError.itemErrorMessage.text = failure.msg
        binding.inclItemError.itemErrorRetryBtn.setOnClickListener { failure.retryAction() }
    }

}