package app.web.drjackycv.features.characters.presentation

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.web.drjackycv.common.exceptions.Failure
import app.web.drjackycv.common.models.fragment.CharacterDetail
import app.web.drjackycv.core.designsystem.*
import app.web.drjackycv.features.characters.R
import app.web.drjackycv.features.characters.databinding.CharacterRowBinding
import app.web.drjackycv.features.characters.databinding.CharactersBinding
import com.google.android.material.transition.platform.Hold
import com.google.android.material.transition.platform.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Characters : Fragment(R.layout.characters) {

    private val charactersListViewModel: CharactersListViewModel by viewModels()
    private val binding by viewBinding(CharactersBinding::bind) {
        cleanUp(it)
    }
    private val charactersAdapter: CharactersAdapter by lazy {
        CharactersAdapter(::navigateToCharacterDetail)
    }

    private fun cleanUp(binding: CharactersBinding?) {
        binding?.rvCharactersList?.adapter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupRecyclerView() {
        postponeEnterTransition()
        binding.inclItemError.itemErrorContainer.gone()
        val footerAdapter = LoadingStateAdapter()
        binding.rvCharactersList.adapter =
            charactersAdapter.withLoadStateFooter(footerAdapter)
        binding.rvCharactersList.layoutManager = GridLayoutManager(context, 2).also {
            it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == charactersAdapter.itemCount && footerAdapter.itemCount > 0) {
                        2
                    } else {
                        1
                    }
                }
            }
        }
        charactersAdapter.addLoadStateListener { adapterLoadingErrorHandling(it) }
        charactersAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        //binding.rvCharactersList.itemAnimator = null
    }

    private fun setupUI() {
        setupRecyclerView()

        charactersListViewModel.run {
            charactersList.collectIn(viewLifecycleOwner) {
                addCharactersList(it)
                view?.doOnPreDraw { startPostponedEnterTransition() }
            }

            failure.collectIn(viewLifecycleOwner) {
                handleFailure(it)
                view?.doOnPreDraw { startPostponedEnterTransition() }
            }
        }
    }

    private fun addCharactersList(uiState: CharactersUiState) {
        when (uiState) {
            is CharactersUiState.Success -> {
                loadingUI(false)
                binding.rvCharactersList.visible()
                charactersAdapter.submitData(lifecycle, uiState.items)
            }
            else -> {
                //no-op: is handled by addLoadStateListener
            }
        }

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

    private fun navigateToCharacterDetail(
        character: CharacterDetail,
        binding: CharacterRowBinding
    ) {
        val extras = FragmentNavigatorExtras(
            binding.imgCharacter to character.image,
            binding.tvName to character.name,
            binding.tvSpecies to character.species,
            binding.tvId to character.id,
            binding.tvGender to character.gender,
        )
        exitTransition = Hold().apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.motion_duration_small).toLong()
        }

        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://app.web.drjackycv/${character.id}".toUri())
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
                charactersListViewModel.handleFailure(this.error) { retryFetchData() }
            }
        }
    }

    private fun retryFetchData() {
        loadingUI(false)
        binding.rvCharactersList.visible()
        charactersAdapter.retry()
    }

    private fun setupErrorItem(failure: Failure) {
        binding.inclItemError.itemErrorMessage.text = failure.msg
        binding.inclItemError.itemErrorRetryBtn.setOnClickListener { failure.retryAction() }
    }

}