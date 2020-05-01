package com.mobile_app.themovie.domain.repository.local

import androidx.lifecycle.LiveData
import com.google.common.base.Optional
import com.mobile_app.themovie.data.entity.Catalog
import io.reactivex.Completable

interface CatalogRepository {

    /**
     * Save catalog
     * @param catalog - expecting catalog
     * @return completable
     */
    fun save(catalog: Catalog): Completable

    /**
     * Find all catalogs
     * @return LiveData list of catalogs
     */
    fun getAll(): LiveData<List<Catalog>>

    /**
     * Find catalog by id
     * @param id - expecting catalog id
     * @return liveData optional of catalog
     */
    fun get(id: Int): LiveData<Optional<Catalog>>

    /**
     * Delete catalog
     * @param id - catalog id
     * @return completable
     */
    fun delete(id: Int): Completable
}