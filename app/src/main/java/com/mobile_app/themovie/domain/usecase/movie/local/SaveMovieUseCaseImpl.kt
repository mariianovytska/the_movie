package com.mobile_app.themovie.domain.usecase.movie.local

import com.mobile_app.themovie.domain.entity.SaveMovieRequest
import com.mobile_app.themovie.domain.repository.local.MovieRepository
import io.reactivex.Completable
import java.util.TreeSet

class SaveMovieUseCaseImpl(private val movieRepository: MovieRepository) : SaveMovieUseCase() {
    override fun build(params: SaveMovieRequest?): Completable {
        return if (params != null) {
            val catalogIds: MutableSet<Int> = TreeSet()
            val movie = params.movie
            movie.catalogIds?.let {
                catalogIds.addAll(it)
            }

            catalogIds.addAll(params.catalogIds)
            movie.catalogIds = catalogIds
            movieRepository.save(movie)
        } else {
            Completable.error(IllegalArgumentException("Param can`t be null"))
        }
    }
}