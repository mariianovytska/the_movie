package com.mobile_app.themovie.domain.usecase.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.google.common.base.Optional
import com.mobile_app.themovie.BuildConfig
import com.mobile_app.themovie.data.entity.Movie
import com.mobile_app.themovie.domain.entity.MovieRequest
import com.mobile_app.themovie.domain.repository.cloud.MovieCloudRepository
import com.mobile_app.themovie.domain.repository.local.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetMovieUseCaseImpl(
    private val movieRepository: MovieRepository,
    private val movieCloudRepository: MovieCloudRepository
) : GetMovieUseCase() {
    override fun build(params: MovieRequest?): LiveData<Optional<Movie>> {
        return params?.let {
            if (params.mode == BuildConfig.OTHER)
                movieRepository.get(params.id)
            else
                LiveDataReactiveStreams.fromPublisher(movieCloudRepository.get(params.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toFlowable())
        } ?: error(IllegalArgumentException("Param can`t be null"))
    }
}