package app.web.drjackycv.features.characters.domain

interface GeneralUseCase<Type, in Params> {

    operator fun invoke(params: Params): Type

}