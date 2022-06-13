package app.web.drjackycv.mvimodularizationtemplate.di

import app.web.drjackycv.features.characters.data.CharactersListRepositoryImpl
import app.web.drjackycv.features.characters.data.CharactersPagingSource
import app.web.drjackycv.features.characters.domain.CharactersListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    @ViewModelScoped
    fun charactersList(
        charactersPagingSource: CharactersPagingSource
    ): CharactersListRepository =
        CharactersListRepositoryImpl(charactersPagingSource)

}