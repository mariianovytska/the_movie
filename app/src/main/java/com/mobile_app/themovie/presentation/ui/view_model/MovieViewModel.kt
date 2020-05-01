package com.mobile_app.themovie.presentation.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.google.common.base.Optional
import com.mobile_app.themovie.data.entity.Movie
import com.mobile_app.themovie.domain.entity.AllMovieRequest
import com.mobile_app.themovie.domain.entity.MovieRequest
import com.mobile_app.themovie.domain.entity.RemoveMovieRequest
import com.mobile_app.themovie.domain.entity.SaveMovieRequest
import com.mobile_app.themovie.domain.usecase.movie.GetMovieUseCase
import com.mobile_app.themovie.domain.usecase.movie.cloud.GetAllMoviesCloudUseCase
import com.mobile_app.themovie.domain.usecase.movie.cloud.GetVideoUseCase
import com.mobile_app.themovie.domain.usecase.movie.cloud.SearchMoviesCloudUseCase
import com.mobile_app.themovie.domain.usecase.movie.local.ContainsMovieUseCase
import com.mobile_app.themovie.domain.usecase.movie.local.GetAllMoviesUseCase
import com.mobile_app.themovie.domain.usecase.movie.local.RemoveOrUpdateMovieUseCase
import com.mobile_app.themovie.domain.usecase.movie.local.SaveMovieUseCase
import com.mobile_app.themovie.presentation.ui.movie_list.paged.MovieCloudDataSource
import com.mobile_app.themovie.presentation.ui.movie_list.paged.SearchCloudDataSource
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class MovieViewModel @Inject constructor(
    private val getAllMoviesCloudUseCase: GetAllMoviesCloudUseCase,
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val saveMovieUseCase: SaveMovieUseCase,
    private val containsMovieUseCase: ContainsMovieUseCase,
    private val getMovieUseCase: GetMovieUseCase,
    private val searchMoviesCloudUseCase: SearchMoviesCloudUseCase,
    private val removeOrUpdateMovieUseCase: RemoveOrUpdateMovieUseCase,
    private val getVideoUseCase: GetVideoUseCase
) : ViewModel() {

    private var popularItems: LiveData<PagedList<Movie>>
    private val disposable = CompositeDisposable()

    init {
        popularItems = configurePagedListBuilder().build()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun saveFavorite(saveMovieRequest: SaveMovieRequest): Completable =
        saveMovieUseCase.execute(saveMovieRequest)

    fun isFavorite(id: Int): LiveData<Boolean> =
        containsMovieUseCase.execute(id)

    fun getMovie(movieRequest: MovieRequest): LiveData<Optional<Movie>> =
        getMovieUseCase.execute(movieRequest)

    fun getPopular() = popularItems
    fun getFavorite(catalogId: Int, query: String = "") =
        getAllMoviesUseCase.execute(
            AllMovieRequest(
                catalogId,
                query
            )
        )

    fun cloudSearch(query: String) = configureSearchPagedListBuilder(query).build()

    fun removeMovie(removeMovieRequest: RemoveMovieRequest) =
        removeOrUpdateMovieUseCase.execute(removeMovieRequest)

    fun getVideo(movieId: Int) =
        getVideoUseCase.execute(movieId)

    private fun configurePagedListBuilder(): LivePagedListBuilder<Int, Movie> {

        val dataSourceFactory = object : DataSource.Factory<Int, Movie>() {

            override fun create(): DataSource<Int, Movie> {
                return MovieCloudDataSource(
                    getAllMoviesCloudUseCase,
                    disposable
                )
            }
        }

        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()

        return LivePagedListBuilder(dataSourceFactory, config)
    }

    private fun configureSearchPagedListBuilder(query: String): LivePagedListBuilder<Int, Movie> {

        val dataSourceFactory = object : DataSource.Factory<Int, Movie>() {

            override fun create(): DataSource<Int, Movie> {
                return SearchCloudDataSource(
                    searchMoviesCloudUseCase,
                    query,
                    disposable
                )
            }
        }

        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()

        return LivePagedListBuilder(dataSourceFactory, config)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }

}