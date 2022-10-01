package app.web.drjackycv.features.character.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import app.web.drjackycv.common.exceptions.Failure
import app.web.drjackycv.core.designsystem.*
import app.web.drjackycv.features.character.R
import app.web.drjackycv.features.character.databinding.CharacterBinding
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Character : Fragment(R.layout.character) {

    private val characterViewModel: CharacterViewModel by viewModels()
    private val binding by viewBinding(CharacterBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        characterViewModel.run {
            character.collectIn(viewLifecycleOwner) {
                displayCharacter(it)
            }

            failure.collectIn(viewLifecycleOwner) {
                handleFailure(it)
            }
        }
    }

    private fun displayCharacter(uiState: CharacterUiState) {
        when (uiState) {
            is CharacterUiState.Success -> {
                loadingUI(false)
                setSharedElementTransitionOnEnter()
                uiState.item?.let {
                    binding.characterContainer.visible()
                    binding.characterContainer.transitionName = it.id
                    binding.imgCharacter.load(
                        url = it.image,
                        placeholderRes = app.web.drjackycv.core.designsystem.R.drawable.ic_no_image,
                        isCircular = true,
                        action = {}
                    )
                    binding.tvName.text = it.name
                    binding.tvGender.text = it.gender
                    binding.tvSpecies.text = it.species
                    binding.tvLocation.text = it.location.name
                    binding.tvStatus.text = it.status
                } ?: run {
                    characterViewModel.handleFailure(Throwable()) { retryFetchData() }
                }
            }
            is CharacterUiState.Error -> {
                loadingUI(false)
                characterViewModel.handleFailure(uiState.error) { retryFetchData() }
            }
            is CharacterUiState.Loading -> {
                loadingUI(true)
            }
        }

    }

    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 400L
            isElevationShadowEnabled = true
            pathMotion = MaterialArcMotion()
            startElevation = 9f
            endElevation = 9f
            scrimColor = Color.TRANSPARENT
        }
    }

    private fun loadingUI(isLoading: Boolean) {
        binding.characterContainer.gone()
        binding.inclItemError.itemErrorContainer.gone()
        if (isLoading) {
            binding.inclItemLoading.itemLoadingContainer.visible()
        } else {
            binding.inclItemLoading.itemLoadingContainer.gone()
        }
    }

    private fun handleFailure(failure: Failure) {
        binding.characterContainer.gone()
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

    private fun retryFetchData() {
        findNavController().popBackStack()
    }

    private fun setupErrorItem(failure: Failure) {
        binding.inclItemError.itemErrorMessage.text = failure.msg
        binding.inclItemError.itemErrorRetryBtn.setOnClickListener { failure.retryAction() }
    }

}