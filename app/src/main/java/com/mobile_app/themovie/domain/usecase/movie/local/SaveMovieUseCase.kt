package com.mobile_app.themovie.domain.usecase.movie.local

import com.mobile_app.themovie.domain.entity.SaveMovieRequest
import com.mobile_app.themovie.domain.usecase.BaseUseCases

abstract class SaveMovieUseCase : BaseUseCases.CompletableUseCase<SaveMovieRequest>()