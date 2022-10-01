package app.web.drjackycv.features.character.presentation

import android.content.res.Resources
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.web.drjackycv.common.exceptions.Failure
import app.web.drjackycv.common.models.fragment.CharacterDetail
import app.web.drjackycv.core.designsystem.R
import app.web.drjackycv.core.network.entity.Result
import app.web.drjackycv.core.network.entity.asResult
import app.web.drjackycv.features.character.domain.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    @Inject
    lateinit var resources: Resources

    private val _failure: Channel<Failure> = Channel(Channel.BUFFERED)
    val failure: Flow<Failure> = _failure.receiveAsFlow()

    private val characterId = savedStateHandle.get<String>("character")!!
    val character: StateFlow<CharacterUiState> =
        getCharacter(characterId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = CharacterUiState.Loading
            )

    private fun getCharacter(id: String) =
        getCharacterUseCase(id)
            .asResult()
            .map { result ->
                when (result) {
                    is Result.Success -> {
                        CharacterUiState.Success(result.data)
                    }
                    is Result.Error -> {
                        CharacterUiState.Error(result.failure)
                    }
                    is Result.Loading -> {
                        CharacterUiState.Loading
                    }
                }
            }

    fun handleFailure(throwable: Throwable, retryAction: () -> Unit) {
        val failure = when (throwable) {
            is Failure.NoInternet -> {
                Failure.NoInternet(resources.getString(R.string.error_no_internet))
            }
            is Failure.Api -> {
                Failure.Api(throwable.msg)
            }
            is Failure.Timeout -> {
                Failure.Timeout(resources.getString(R.string.error_timeout))
            }
            is Failure.Unknown -> {
                Failure.Unknown(resources.getString(R.string.error_unknown))
            }
            else -> {
                Failure.Unknown(resources.getString(R.string.error_unknown))
            }
        }

        failure.retryAction = retryAction
        viewModelScope.launch {
            _failure.send(failure)
        }
    }

}

sealed interface CharacterUiState {
    data class Success(val item: CharacterDetail? = null) : CharacterUiState
    data class Error(val error: Failure) : CharacterUiState
    object Loading : CharacterUiState
}