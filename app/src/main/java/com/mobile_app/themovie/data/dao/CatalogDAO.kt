package com.mobile_app.themovie.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.google.common.base.Optional
import com.mobile_app.themovie.data.entity.Catalog
import io.reactivex.Completable

@Dao
abstract class CatalogDAO {
    @Query("select * from catalog")
    abstract fun getAll(): LiveData<List<Catalog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(catalog: Catalog): Completable

    @Query("SELECT * from catalog WHERE id = :id")
    abstract fun get(id: Int): LiveData<Optional<Catalog>>

    @Query("DELETE FROM catalog WHERE id = :id")
    abstract fun delete(id: Int): Completable
}