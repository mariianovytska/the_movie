package com.mobile_app.themovie.data.database

import androidx.lifecycle.LiveData
import com.google.common.base.Optional
import com.mobile_app.themovie.data.dao.MovieDAO
import com.mobile_app.themovie.data.datasource.MovieDataSource
import com.mobile_app.themovie.data.entity.Movie
import io.reactivex.Completable

open class MovieDatabase(private val movieDAO: MovieDAO) :
    MovieDataSource {

    override fun save(movie: Movie): Completable {
        return movieDAO.save(movie)
    }

    override fun get(id: Int): LiveData<Optional<Movie>> {
        return movieDAO.get(id)
    }

    override fun getAll(): LiveData<List<Movie>> {
        return movieDAO.getAll()
    }

    override fun delete(id: Int): Completable {
        return movieDAO.delete(id)
    }
}