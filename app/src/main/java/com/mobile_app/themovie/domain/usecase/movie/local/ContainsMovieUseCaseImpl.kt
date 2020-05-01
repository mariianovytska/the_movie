package com.mobile_app.themovie.domain.usecase.movie.local

import androidx.lifecycle.LiveData
import com.mobile_app.themovie.domain.repository.local.MovieRepository

class ContainsMovieUseCaseImpl(private val movieRepository: MovieRepository) : ContainsMovieUseCase() {
    override fun build(params: Int?): LiveData<Boolean> {
        return if (params != null) {
             movieRepository.contains(params)
        } else {
            error(IllegalArgumentException("Param can`t be null"))
        }
    }
}