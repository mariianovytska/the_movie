package com.mobile_app.themovie.domain.usecase.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mobile_app.themovie.domain.repository.local.CatalogRepository

class GetCatalogNameUseCaseImpl(private val catalogRepository: CatalogRepository) :
    GetCatalogNameUseCase() {
    override fun build(params: Int?): LiveData<String> =
        params?.let {
            Transformations.map(
                catalogRepository.get(it)
            ) {
                if (it.isPresent) it.get().title else ""
            }
        } ?: error(IllegalArgumentException("Param can`t be null"))
}