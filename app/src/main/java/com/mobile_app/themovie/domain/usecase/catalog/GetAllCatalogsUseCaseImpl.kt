package com.mobile_app.themovie.domain.usecase.catalog

import androidx.lifecycle.LiveData
import com.mobile_app.themovie.data.entity.Catalog
import com.mobile_app.themovie.domain.repository.local.CatalogRepository

class GetAllCatalogsUseCaseImpl(private val catalogRepository: CatalogRepository) : GetAllCatalogsUseCase() {
    override fun build(params: Unit?): LiveData<List<Catalog>> {
        return catalogRepository.getAll()
    }
}