package com.mobile_app.themovie.domain.repository.cloud

import com.google.common.base.Optional
import com.mobile_app.themovie.data.entity.CloudResponse
import com.mobile_app.themovie.data.entity.Movie
import com.mobile_app.themovie.data.entity.Video
import com.mobile_app.themovie.data.entity.VideoResponse
import io.reactivex.Single
import io.reactivex.Observable

interface MovieCloudRepository {

    /**
     * Find movie by id
     * @param id - expecting movie id
     * @return single optional of movie
     */
    fun get(id: Int): Single<Optional<Movie>>

    /**
     * Find all movies
     * @param page - page number
     * @return observable response
     */
    fun getAll(page: Int): Observable<CloudResponse<Movie>>

    /**
     * Find all by query
     * @param page - page number
     * @param query - query in search
     * @return observable response
     */
    fun search(page: Int, query: String = ""): Observable<CloudResponse<Movie>>

    /**
     * Find video related to the movie
     * @param movieId - id of the movie
     * @return observable videoResponse
     */
    fun getVideo(movieId: Int): Observable<VideoResponse<Video>>
}