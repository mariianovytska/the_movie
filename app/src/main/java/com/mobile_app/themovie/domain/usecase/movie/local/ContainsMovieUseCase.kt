package com.mobile_app.themovie.domain.usecase.movie.local

import com.mobile_app.themovie.domain.usecase.BaseUseCases

abstract class ContainsMovieUseCase : BaseUseCases.LiveDataUseCase<Int, Boolean>()