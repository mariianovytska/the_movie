package com.mobile_app.themovie.data.repository.cloud

import com.google.common.base.Optional
import com.mobile_app.themovie.data.dao.MovieCloudDAO
import com.mobile_app.themovie.data.entity.CloudResponse
import com.mobile_app.themovie.data.entity.Movie
import com.mobile_app.themovie.data.entity.Video
import com.mobile_app.themovie.data.entity.VideoResponse
import com.mobile_app.themovie.domain.repository.cloud.MovieCloudRepository
import io.reactivex.Single
import io.reactivex.Observable

class MovieCloudRepositoryImpl(
    private val movieCloudDAO: MovieCloudDAO
) : MovieCloudRepository {

    override fun get(id: Int): Single<Optional<Movie>> =
        movieCloudDAO.get(id)

    override fun getAll(page: Int): Observable<CloudResponse<Movie>> =
        movieCloudDAO.getAll(page)

    override fun search(page: Int, query: String): Observable<CloudResponse<Movie>> =
        movieCloudDAO.search(page, query)

    override fun getVideo(movieId: Int): Observable<VideoResponse<Video>> {
        return movieCloudDAO.getVideo(movieId)
    }
}
