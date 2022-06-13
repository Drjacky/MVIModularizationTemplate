package app.web.drjackycv.features.characters.domain

import androidx.paging.PagingData
import app.web.drjackycv.common.models.fragment.CharacterDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersListUseCase @Inject constructor(
    private val charactersListRepository: CharactersListRepository,
) : GeneralUseCase<Flow<PagingData<CharacterDetail>>, Unit> {

    override fun invoke(params: Unit): Flow<PagingData<CharacterDetail>> =
        charactersListRepository.getCharactersList()

}