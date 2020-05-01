package com.mobile_app.themovie.domain.usecase.catalog

import com.google.common.base.Optional
import com.mobile_app.themovie.data.entity.Catalog
import com.mobile_app.themovie.data.entity.Movie
import com.mobile_app.themovie.domain.usecase.BaseUseCases

abstract class GetMovieAndAvailableCatalogsUseCase : BaseUseCases.LiveDataUseCase<Int, Pair<Optional<Movie>, List<Catalog>>>()