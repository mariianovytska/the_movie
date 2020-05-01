package com.mobile_app.themovie.data.repository.local

import androidx.lifecycle.LiveData
import com.google.common.base.Optional
import com.mobile_app.themovie.data.datasource.CatalogDataSource
import com.mobile_app.themovie.data.entity.Catalog
import com.mobile_app.themovie.domain.repository.local.CatalogRepository
import io.reactivex.Completable

class CatalogRepositoryImpl(private val catalogDataSource: CatalogDataSource) :
    CatalogRepository {

    override fun save(catalog: Catalog): Completable {
        return catalogDataSource.save(catalog)
    }

    override fun getAll(): LiveData<List<Catalog>> {
        return catalogDataSource.getAll()
    }

    override fun get(id: Int): LiveData<Optional<Catalog>> {
        return catalogDataSource.get(id)
    }

    override fun delete(id: Int): Completable {
        return catalogDataSource.delete(id)
    }
}
