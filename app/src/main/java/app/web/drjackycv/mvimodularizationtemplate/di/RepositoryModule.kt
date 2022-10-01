package app.web.drjackycv.mvimodularizationtemplate.di

import app.web.drjackycv.core.designsystem.IoDispatcher
import app.web.drjackycv.features.character.data.CharacterRepositoryImpl
import app.web.drjackycv.features.character.domain.CharacterRepository
import app.web.drjackycv.features.characters.data.CharactersListRepositoryImpl
import app.web.drjackycv.features.characters.data.CharactersPagingSource
import app.web.drjackycv.features.characters.domain.CharactersListRepository
import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    @ViewModelScoped
    fun charactersList(
        charactersPagingSource: CharactersPagingSource
    ): CharactersListRepository =
        CharactersListRepositoryImpl(charactersPagingSource)

    @Provides
    @ViewModelScoped
    fun character(
        apolloClient: ApolloClient,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): CharacterRepository =
        CharacterRepositoryImpl(apolloClient, ioDispatcher)

}