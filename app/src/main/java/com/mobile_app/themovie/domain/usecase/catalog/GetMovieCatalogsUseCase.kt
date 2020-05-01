package com.mobile_app.themovie.domain.usecase.catalog

import com.mobile_app.themovie.data.entity.Catalog
import com.mobile_app.themovie.domain.usecase.BaseUseCases

abstract class GetMovieCatalogsUseCase : BaseUseCases.LiveDataUseCase<Int, List<Catalog>>()