package com.mobile_app.themovie.domain.usecase.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.common.base.Optional
import com.mobile_app.themovie.data.entity.Catalog
import com.mobile_app.themovie.data.entity.Movie
import com.mobile_app.themovie.domain.repository.local.CatalogRepository
import com.mobile_app.themovie.domain.repository.local.MovieRepository

class GetMovieAndAvailableCatalogsUseCaseImpl(
    private val movieRepository: MovieRepository,
    private val catalogRepository: CatalogRepository
) : GetMovieAndAvailableCatalogsUseCase() {
    override fun build(params: Int?): LiveData<Pair<Optional<Movie>, List<Catalog>>> =
        params?.let {
            Transformations.switchMap(movieRepository.get(params)) { movieOptional ->
                var movieCatalogs: List<Catalog>
                Transformations.map(catalogRepository.getAll()) { allCatalogs ->
                    movieCatalogs = allCatalogs
                    if (movieOptional.isPresent) {
                        movieOptional.get().catalogIds?.let { catalogIds ->
                            movieCatalogs =
                                allCatalogs.filter { catalog -> !catalogIds.contains(catalog.id) }
                        }
                    }
                    Pair(movieOptional, movieCatalogs)
                }
            }
        } ?: error(IllegalArgumentException("Param can`t be null"))

}