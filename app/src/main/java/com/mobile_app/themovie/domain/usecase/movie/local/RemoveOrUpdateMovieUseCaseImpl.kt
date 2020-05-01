package com.mobile_app.themovie.domain.usecase.movie.local

import com.mobile_app.themovie.domain.entity.RemoveMovieRequest
import com.mobile_app.themovie.domain.repository.local.MovieRepository
import io.reactivex.Completable

class RemoveOrUpdateMovieUseCaseImpl(private val movieRepository: MovieRepository) :
    RemoveOrUpdateMovieUseCase() {
    override fun build(params: RemoveMovieRequest?): Completable {
        return params?.let {
            movieRepository.deleteOrUpdate(params.movie, params.catalogId)
        } ?: Completable.error(IllegalArgumentException("Param can`t be null"))
    }

}