package com.mobile_app.themovie.presentation.ui.movie_list.paged

import androidx.paging.PageKeyedDataSource
import com.mobile_app.themovie.data.entity.Movie
import com.mobile_app.themovie.domain.entity.SearchRequest
import com.mobile_app.themovie.domain.usecase.movie.cloud.SearchMoviesCloudUseCase
import io.reactivex.disposables.CompositeDisposable

class SearchCloudDataSource(
    private val searchMoviesCloudUseCase: SearchMoviesCloudUseCase,
    private val query: String,
    private val disposable: CompositeDisposable
) : PageKeyedDataSource<Int, Movie>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {

        val currentPage = 1
        val nextPage = currentPage + 1

        disposable.add(
            loadData(currentPage).subscribe({
                callback.onResult(it, null, nextPage)
            }, {})
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val currentPage = params.key
        val nextPage = currentPage + 1

        disposable.add(
            loadData(currentPage).subscribe({
                callback.onResult(it, nextPage)
            }, {})
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val currentPage = params.key
        val nextPage = currentPage - 1

        disposable.add(
            loadData(currentPage).subscribe({
                callback.onResult(it, nextPage)
            }, {})
        )
    }

    private fun loadData(pageNumber: Int) =
        searchMoviesCloudUseCase.execute(SearchRequest(pageNumber, query)).map { it.results }
}