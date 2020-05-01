package com.mobile_app.themovie.domain.usecase.movie.cloud

import com.mobile_app.themovie.data.entity.CloudResponse
import com.mobile_app.themovie.data.entity.Movie
import com.mobile_app.themovie.domain.entity.SearchRequest
import com.mobile_app.themovie.domain.repository.cloud.MovieCloudRepository
import io.reactivex.Observable
import java.lang.IllegalArgumentException

class SearchMoviesCloudUseCaseImpl(private val movieCloudRepository: MovieCloudRepository) : SearchMoviesCloudUseCase() {
    override fun build(params: SearchRequest?): Observable<CloudResponse<Movie>> {
        return params?.let {
            movieCloudRepository.search(it.page, it.query)
        } ?: Observable.error(IllegalArgumentException("Params can`t be null"))
    }
}