package com.mobile_app.themovie.domain.usecase.movie.cloud

import com.mobile_app.themovie.data.entity.Video
import com.mobile_app.themovie.domain.repository.cloud.MovieCloudRepository
import io.reactivex.Observable
import java.lang.IllegalArgumentException

class GetVideoUseCaseImpl(private val movieCloudRepository: MovieCloudRepository) : GetVideoUseCase() {
    override fun build(params: Int?): Observable<List<Video>> {
        return params?.let {
            movieCloudRepository.getVideo(it).map { it.results }
        } ?: Observable.error(IllegalArgumentException("Params can`t be null"))
    }
}