package com.mobile_app.themovie.domain.usecase.movie.local

import com.mobile_app.themovie.domain.entity.RemoveMovieRequest
import com.mobile_app.themovie.domain.usecase.BaseUseCases

abstract class RemoveOrUpdateMovieUseCase : BaseUseCases.CompletableUseCase<RemoveMovieRequest>()