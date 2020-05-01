package com.mobile_app.themovie.domain.usecase.movie.cloud

import com.mobile_app.themovie.data.entity.CloudResponse
import com.mobile_app.themovie.data.entity.Movie
import com.mobile_app.themovie.domain.usecase.BaseUseCases

abstract class GetAllMoviesCloudUseCase : BaseUseCases.ObservableUseCase<Int, CloudResponse<Movie>>()