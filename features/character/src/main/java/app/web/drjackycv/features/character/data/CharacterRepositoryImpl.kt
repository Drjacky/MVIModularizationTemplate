package app.web.drjackycv.features.character.data

import app.web.drjackycv.common.models.GetCharacterQuery
import app.web.drjackycv.common.models.fragment.CharacterDetail
import app.web.drjackycv.core.designsystem.IoDispatcher
import app.web.drjackycv.features.character.domain.CharacterRepository
import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CharacterRepository {

    override fun getCharacter(id: String): Flow<CharacterDetail> = //TODO: Handle exception
        flow {
            val response: GetCharacterQuery.Character =
                apolloClient.query(GetCharacterQuery(id))
                    .execute().dataAssertNoErrors.character
            val responseCharacters = response.characterDetail

            emit(responseCharacters)

        }
}