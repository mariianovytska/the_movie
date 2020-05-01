package com.mobile_app.themovie.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.google.common.base.Optional
import com.mobile_app.themovie.data.entity.Movie
import io.reactivex.Completable

@Dao
abstract class MovieDAO {
    @Query("select * from movie")
    abstract fun getAll(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(movie: Movie): Completable

    @Query("SELECT * from movie WHERE id = :id")
    abstract fun get(id: Int): LiveData<Optional<Movie>>

    @Query("DELETE FROM movie WHERE id = :id")
    abstract fun delete(id: Int): Completable
}