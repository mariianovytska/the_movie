package com.mobile_app.themovie.domain.usecase.movie.cloud

import com.mobile_app.themovie.data.entity.CloudResponse
import com.mobile_app.themovie.data.entity.Movie
import com.mobile_app.themovie.domain.repository.cloud.MovieCloudRepository
import io.reactivex.Observable
import java.lang.IllegalArgumentException

class GetAllMoviesCloudUseCaseImpl(private val movieCloudRepository: MovieCloudRepository) : GetAllMoviesCloudUseCase() {
    override fun build(params: Int?): Observable<CloudResponse<Movie>> {
        return params?.let {
            movieCloudRepository.getAll(it)
        } ?: Observable.error(IllegalArgumentException("Params can`t be null"))
    }
}