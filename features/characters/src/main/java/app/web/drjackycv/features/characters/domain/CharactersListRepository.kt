package app.web.drjackycv.features.characters.domain

import androidx.paging.PagingData
import app.web.drjackycv.common.models.fragment.CharacterDetail
import kotlinx.coroutines.flow.Flow

interface CharactersListRepository {

    fun getCharactersList(): Flow<PagingData<CharacterDetail>>

}