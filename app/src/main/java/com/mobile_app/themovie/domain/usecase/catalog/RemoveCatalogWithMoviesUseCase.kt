package com.mobile_app.themovie.domain.usecase.catalog

import com.mobile_app.themovie.domain.entity.RemoveCatalogRequest
import com.mobile_app.themovie.domain.usecase.BaseUseCases

abstract class RemoveCatalogWithMoviesUseCase : BaseUseCases.CompletableUseCase<RemoveCatalogRequest>()