package com.mobile_app.themovie.presentation.ui.movie_list.paged

import androidx.paging.PageKeyedDataSource
import com.mobile_app.themovie.data.entity.Movie

class MovieLocalDataSource(
    private val allMovies: List<Movie>,
    private val catalogId: Int,
    private val query: String
) : PageKeyedDataSource<Int, Movie>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        callback.onResult(filteredData(), null, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {}

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {}

    private fun filteredData() = allMovies.filter {
        it.catalogIds != null &&
                it.catalogIds!!.contains(catalogId) &&
                it.title.toUpperCase().contains(query.toUpperCase())
    }
}