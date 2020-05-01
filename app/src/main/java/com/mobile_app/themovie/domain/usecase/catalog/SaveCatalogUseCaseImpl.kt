package com.mobile_app.themovie.domain.usecase.catalog

import com.mobile_app.themovie.data.entity.Catalog
import com.mobile_app.themovie.domain.repository.local.CatalogRepository
import io.reactivex.Completable

class SaveCatalogUseCaseImpl(private val catalogRepository: CatalogRepository) : SaveCatalogUseCase() {
    override fun build(params: Catalog?): Completable {
        return if (params != null) {
            catalogRepository.save(params)
        } else {
            Completable.error(IllegalArgumentException("Param can`t be null"))
        }
    }
}