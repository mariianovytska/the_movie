package com.mobile_app.themovie.domain.usecase.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mobile_app.themovie.data.entity.Catalog
import com.mobile_app.themovie.domain.repository.local.CatalogRepository
import com.mobile_app.themovie.domain.repository.local.MovieRepository

class GetMovieCatalogsUseCaseImpl(
    private val movieRepository: MovieRepository,
    private val catalogRepository: CatalogRepository
) : GetMovieCatalogsUseCase() {
    override fun build(params: Int?): LiveData<List<Catalog>> =
        params?.let {
            Transformations.switchMap(movieRepository.get(params)) { movie ->
                var movieCatalogs: List<Catalog>
                Transformations.map(catalogRepository.getAll()) { allCatalogs ->
                    movieCatalogs = allCatalogs
                    if (movie.isPresent) {
                        movie.get().catalogIds?.let { catalogIds ->
                            movieCatalogs =
                                allCatalogs.filter { catalog -> catalogIds.contains(catalog.id) }
                        }
                    } else {
                        movieCatalogs = listOf()
                    }
                    movieCatalogs
                }
            }
        } ?: error(IllegalArgumentException("Param can`t be null"))

}