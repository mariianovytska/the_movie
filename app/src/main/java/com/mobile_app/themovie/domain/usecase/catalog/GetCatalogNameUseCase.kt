package com.mobile_app.themovie.domain.usecase.catalog

import com.mobile_app.themovie.domain.usecase.BaseUseCases

abstract class GetCatalogNameUseCase : BaseUseCases.LiveDataUseCase<Int, String>()