package com.mobile_app.themovie.domain.usecase.catalog

import com.mobile_app.themovie.data.entity.Catalog
import com.mobile_app.themovie.domain.usecase.BaseUseCases

abstract class GetAllCatalogsUseCase : BaseUseCases.LiveDataUseCase<Unit, List<Catalog>>()