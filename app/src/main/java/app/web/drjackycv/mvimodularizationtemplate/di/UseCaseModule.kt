package app.web.drjackycv.mvimodularizationtemplate.di

import app.web.drjackycv.features.character.domain.CharacterRepository
import app.web.drjackycv.features.character.domain.GetCharacterUseCase
import app.web.drjackycv.features.characters.domain.CharactersListRepository
import app.web.drjackycv.features.characters.domain.GetCharactersListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun getCharactersListUseCase(
        charactersListRepository: CharactersListRepository,
    ): GetCharactersListUseCase =
        GetCharactersListUseCase(charactersListRepository::getCharactersList)

    @Provides
    @ViewModelScoped
    fun getCharacterUseCase(
        characterRepository: CharacterRepository,
    ): GetCharacterUseCase =
        GetCharacterUseCase(characterRepository::getCharacter)

}