package com.mobile_app.themovie.presentation.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mobile_app.themovie.data.entity.Catalog
import com.mobile_app.themovie.domain.entity.RemoveCatalogRequest
import com.mobile_app.themovie.domain.usecase.catalog.*
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CatalogViewModel @Inject constructor(
    getAllCatalogsUseCase: GetAllCatalogsUseCase,
    private val saveCatalogUseCase: SaveCatalogUseCase,
    private val getMovieCatalogsUseCase: GetMovieCatalogsUseCase,
    private val removeCatalogUseCase: RemoveCatalogWithMoviesUseCase,
    private val getCatalogNameUseCase: GetCatalogNameUseCase,
    private val getMovieAndAvailableCatalogsUseCase: GetMovieAndAvailableCatalogsUseCase
) : ViewModel() {
    private var catalogItems: LiveData<List<Catalog>>

    init {
        catalogItems = getAllCatalogsUseCase.execute()
    }

    fun save(catalog: Catalog?): Completable = saveCatalogUseCase.execute(catalog)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getAll() = catalogItems


    fun getMovieCatalogs(movieId: Int): LiveData<List<Catalog>> =
        getMovieCatalogsUseCase.execute(movieId)

    fun getMovieAndAvailableCatalogs(movieId: Int) =
        getMovieAndAvailableCatalogsUseCase.execute(movieId)

    fun removeCatalog(removeRequest: RemoveCatalogRequest): Completable =
        removeCatalogUseCase.execute(removeRequest)

    fun getCatalogName(id: Int): LiveData<String> =
        getCatalogNameUseCase.execute(id)
}