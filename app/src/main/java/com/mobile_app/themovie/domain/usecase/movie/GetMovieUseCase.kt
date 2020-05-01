package com.mobile_app.themovie.domain.usecase.movie

import com.google.common.base.Optional
import com.mobile_app.themovie.data.entity.Movie
import com.mobile_app.themovie.domain.entity.MovieRequest
import com.mobile_app.themovie.domain.usecase.BaseUseCases

abstract class GetMovieUseCase : BaseUseCases.LiveDataUseCase<MovieRequest, Optional<Movie>>()