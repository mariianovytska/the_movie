package com.mobile_app.themovie.data.database

import androidx.lifecycle.LiveData
import com.google.common.base.Optional
import com.mobile_app.themovie.data.dao.CatalogDAO
import com.mobile_app.themovie.data.datasource.CatalogDataSource
import com.mobile_app.themovie.data.entity.Catalog
import io.reactivex.Completable

open class CatalogDatabase(
    private val catalogDAO: CatalogDAO
) : CatalogDataSource {

    override fun save(catalog: Catalog): Completable {
        return catalogDAO.save(catalog)
    }

    override fun getAll(): LiveData<List<Catalog>> {
        return catalogDAO.getAll()
    }

    override fun get(id: Int): LiveData<Optional<Catalog>> {
        return catalogDAO.get(id)
    }

    override fun delete(id: Int): Completable {
        return catalogDAO.delete(id)
    }
}