package com.mobile_app.themovie.domain.usecase.movie.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mobile_app.themovie.data.entity.Movie
import com.mobile_app.themovie.domain.entity.AllMovieRequest
import com.mobile_app.themovie.domain.repository.local.MovieRepository
import com.mobile_app.themovie.presentation.ui.movie_list.paged.MovieLocalDataSource

class GetAllMoviesUseCaseImpl(private val movieRepository: MovieRepository) :
    GetAllMoviesUseCase() {

    override fun build(params: AllMovieRequest?): LiveData<PagedList<Movie>> {
        return params?.let {
            Transformations.switchMap(
                movieRepository.getAll()
            ) { configurePagedListBuilder(it, params.catalogId, params.query) }
        } ?: error(IllegalArgumentException("Param can`t be null"))
    }

    private fun configurePagedListBuilder(
        pagedList: List<Movie>,
        catalogId: Int,
        query: String
    ): LiveData<PagedList<Movie>> {
        val dataSourceFactory = object : DataSource.Factory<Int, Movie>() {
            override fun create(): DataSource<Int, Movie> {
                return MovieLocalDataSource(
                    pagedList, catalogId, query
                )
            }
        }
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .build()
        return LivePagedListBuilder(dataSourceFactory, config).build()
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}