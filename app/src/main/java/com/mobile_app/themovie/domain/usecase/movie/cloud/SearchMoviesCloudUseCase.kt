package com.mobile_app.themovie.domain.usecase.movie.cloud

import com.mobile_app.themovie.data.entity.CloudResponse
import com.mobile_app.themovie.data.entity.Movie
import com.mobile_app.themovie.domain.entity.SearchRequest
import com.mobile_app.themovie.domain.usecase.BaseUseCases

abstract class SearchMoviesCloudUseCase : BaseUseCases.ObservableUseCase<SearchRequest, CloudResponse<Movie>>()