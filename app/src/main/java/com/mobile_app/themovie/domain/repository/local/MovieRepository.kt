package com.mobile_app.themovie.domain.repository.local

import androidx.lifecycle.LiveData
import com.google.common.base.Optional
import com.mobile_app.themovie.data.entity.Movie
import io.reactivex.Completable

interface MovieRepository {

    /**
     * Save movie
     * @param movie - expecting movie
     * @return completable
     */
    fun save(movie: Movie): Completable

    /**
     * Find movie by id
     * @param id - expecting movie id
     * @return liveData optional of movie
     */
    fun get(id: Int): LiveData<Optional<Movie>>

    /**
     * Find all movies
     * @return liveData list of movies
     */
    fun getAll(): LiveData<List<Movie>>

    /**
     * Contain movie with given id
     * @param id - movie id
     * @return true if contains, false if not
     */
    fun contains(id: Int): LiveData<Boolean>

    /**
     * Delete movie
     * @param id - expecting movie id
     * @return completable
     */
    fun delete(id: Int): Completable

    /**
     * Delete movie if contains empty catalog list and update catalogIds if catalogIds not empty list
     * @param movie - expecting movie
     * @param catalogId - id of from which movie should be removed
     * @return completable
     */
    fun deleteOrUpdate(movie: Movie, catalogId: Int): Completable
}