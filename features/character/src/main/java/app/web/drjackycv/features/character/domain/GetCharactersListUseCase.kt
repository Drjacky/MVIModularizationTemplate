package app.web.drjackycv.features.character.domain

import app.web.drjackycv.common.models.fragment.CharacterDetail
import kotlinx.coroutines.flow.Flow

fun interface GetCharacterUseCase : (String) -> Flow<CharacterDetail>