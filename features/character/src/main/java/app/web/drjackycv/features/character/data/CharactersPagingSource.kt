package app.web.drjackycv.features.character.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.web.drjackycv.common.exceptions.Failure
import app.web.drjackycv.common.models.GetCharactersQuery
import app.web.drjackycv.common.models.fragment.CharacterDetail
import app.web.drjackycv.core.designsystem.IoDispatcher
import com.apollographql.apollo3.ApolloClient
import io.reactivex.rxjava3.annotations.NonNull
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import javax.inject.Singleton


private const val STARTING_PAGE_INDEX = 1

@Singleton
class CharactersPagingSource @Inject constructor(
    private val apolloClient: ApolloClient,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
    //private val query: String
) : PagingSource<Int, CharacterDetail>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDetail> =
        withContext(ioDispatcher) {
            val position = params.key ?: STARTING_PAGE_INDEX
            //val apiQuery = query

            return@withContext try {
                val response: GetCharactersQuery.Characters =
                    apolloClient.query(GetCharactersQuery(position))
                        .execute().dataAssertNoErrors.characters
                val responseCharacters = response.results.mapNotNull { it?.characterDetail }

                toLoadResult(responseCharacters, position)
            } catch (e: Exception) {
                when (e.cause?.cause) {
                    is UnknownHostException, is SocketTimeoutException -> {
                        LoadResult.Error(
                            Failure.NoInternet(e.message)
                        )
                    }
                    is TimeoutException -> {
                        LoadResult.Error(
                            Failure.Timeout(e.message)
                        )
                    }
                    else -> {
                        LoadResult.Error(
                            Failure.Unknown(e.message)
                        )
                    }
                }
            }
        }

    override val jumpingSupported = true

    override fun getRefreshKey(state: PagingState<Int, CharacterDetail>): Int? =
        state.anchorPosition

    private fun toLoadResult(
        @NonNull response: List<CharacterDetail>,
        position: Int,
    ): LoadResult<Int, CharacterDetail> {
        return LoadResult.Page(
            data = response,
            prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
            nextKey = if (response.isEmpty()) null else position + 1,
            itemsBefore = LoadResult.Page.COUNT_UNDEFINED,
            itemsAfter = LoadResult.Page.COUNT_UNDEFINED
        )
    }

}