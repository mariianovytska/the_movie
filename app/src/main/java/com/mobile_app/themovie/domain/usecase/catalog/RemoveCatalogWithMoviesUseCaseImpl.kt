package com.mobile_app.themovie.domain.usecase.catalog

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.Transformations
import com.mobile_app.themovie.domain.entity.RemoveCatalogRequest
import com.mobile_app.themovie.domain.repository.local.CatalogRepository
import com.mobile_app.themovie.domain.repository.local.MovieRepository
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RemoveCatalogWithMoviesUseCaseImpl(
    private val catalogRepository: CatalogRepository,
    private val movieRepository: MovieRepository
) : RemoveCatalogWithMoviesUseCase() {
    override fun build(params: RemoveCatalogRequest?): Completable =
        (params?.let {
            val result = Transformations.switchMap(
                movieRepository.getAll()
            ) { pagedListMovies ->
                var deleteAll = catalogRepository.delete(params.id)
                pagedListMovies.forEach { movie ->
                    movie.catalogIds?.let { ids ->
                        if (ids.contains(params.id)) {
                            deleteAll = Completable.mergeArray(
                                deleteAll,
                                movieRepository.deleteOrUpdate(movie, params.id)
                            )
                        }
                    }
                }
                LiveDataReactiveStreams.fromPublisher<Completable>(
                    deleteAll
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .toFlowable()
                )
            }
            Completable.fromPublisher(LiveDataReactiveStreams.toPublisher(params.lifecycleOwner, result))
        } ?: Completable.error(IllegalArgumentException("Param can`t be null")))
}