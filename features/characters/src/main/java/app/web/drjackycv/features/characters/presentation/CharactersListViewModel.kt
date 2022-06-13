package app.web.drjackycv.features.characters.presentation

import android.content.res.Resources
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.web.drjackycv.common.exceptions.Failure
import app.web.drjackycv.common.models.fragment.CharacterDetail
import app.web.drjackycv.features.characters.R
import app.web.drjackycv.features.characters.domain.GetCharactersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    private val getCharactersListUseCase: GetCharactersListUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    @Inject
    lateinit var resources: Resources

    private val _failure: Channel<Failure> = Channel(Channel.BUFFERED)
    val failure: Flow<Failure> = _failure.receiveAsFlow()
    private val _charactersList =
        MutableStateFlow<PagingData<CharacterDetail>>(PagingData.empty())
    val charactersList: Flow<PagingData<CharacterDetail>> = _charactersList


    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            _charactersList.value = getCharactersListUseCase(Unit)
                .cachedIn(viewModelScope).first()
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