package com.mobile_app.themovie.data.datasource

import androidx.lifecycle.LiveData
import com.google.common.base.Optional
import com.mobile_app.themovie.data.entity.Movie
import io.reactivex.Completable

interface MovieDataSource {

    fun save(movie: Movie): Completable
    fun get(id: Int): LiveData<Optional<Movie>>
    fun getAll(): LiveData<List<Movie>>
    fun delete(id: Int): Completable
}