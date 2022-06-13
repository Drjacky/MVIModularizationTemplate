package app.web.drjackycv.features.characters.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.web.drjackycv.common.models.fragment.CharacterDetail
import app.web.drjackycv.core.designsystem.allowReads
import app.web.drjackycv.features.characters.domain.CharactersListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersListRepositoryImpl @Inject constructor(
    private val charactersPagingSource: CharactersPagingSource
) : CharactersListRepository {

    override fun getCharactersList(ids: String): Flow<PagingData<CharacterDetail>> =
        allowReads {
            Pager(
                config = PagingConfig(
                    pageSize = 10,
                    enablePlaceholders = false,
                    maxSize = 30,
                    prefetchDistance = 5,
                    initialLoadSize = 10,
                    jumpThreshold = 60
                ),
                pagingSourceFactory = { charactersPagingSource }
            ).flow
        }
}