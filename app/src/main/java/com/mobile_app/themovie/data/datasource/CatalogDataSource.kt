package com.mobile_app.themovie.data.datasource

import androidx.lifecycle.LiveData
import com.google.common.base.Optional
import com.mobile_app.themovie.data.entity.Catalog
import io.reactivex.Completable

interface CatalogDataSource {

    fun save(catalog: Catalog): Completable
    fun getAll(): LiveData<List<Catalog>>
    fun get(id: Int): LiveData<Optional<Catalog>>
    fun delete(id: Int): Completable
}