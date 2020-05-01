package com.mobile_app.themovie.domain.usecase.movie.cloud

import com.mobile_app.themovie.data.entity.Video
import com.mobile_app.themovie.domain.usecase.BaseUseCases

abstract class GetVideoUseCase : BaseUseCases.ObservableUseCase<Int, List<Video>>()