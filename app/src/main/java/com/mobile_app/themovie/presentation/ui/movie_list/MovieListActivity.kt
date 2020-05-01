package com.mobile_app.themovie.presentation.ui.movie_list

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.common.base.Optional
import com.mobile_app.themovie.BuildConfig
import com.mobile_app.themovie.R
import com.mobile_app.themovie.data.entity.Catalog
import com.mobile_app.themovie.data.entity.Movie
import com.mobile_app.themovie.databinding.ActivityMovieListBinding
import com.mobile_app.themovie.domain.entity.RemoveMovieRequest
import com.mobile_app.themovie.domain.entity.SaveMovieRequest
import com.mobile_app.themovie.presentation.di.AppModule
import com.mobile_app.themovie.presentation.di.DaggerAppComponent
import com.mobile_app.themovie.presentation.navigation.AppRouter
import com.mobile_app.themovie.presentation.ui.view_model.CatalogViewModel
import com.mobile_app.themovie.presentation.ui.view_model.MovieViewModel
import com.mobile_app.themovie.presentation.util.ImageLoader
import com.mobile_app.themovie.presentation.util.MovieConst
import com.mobile_app.themovie.presentation.util.showEmptyState
import com.mobile_app.themovie.presentation.util.showMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


class MovieListActivity : AppCompatActivity() {

    private val favoriteListener =
        PublishSubject.create<Pair<Movie, Int>>()
    private val movieDetailsListener =
        PublishSubject.create<Int>()
    private val isCheckedListener =
        PublishSubject.create<Boolean>()
    private val removeMovieListener =
        PublishSubject.create<Movie>()

    private lateinit var binding: ActivityMovieListBinding
    private lateinit var movieAdapter: MovieListAdapter
    private lateinit var mode: String
    private var catalogId: Int = -1
    private var disposable = CompositeDisposable()
    private lateinit var movies: LiveData<PagedList<Movie>>

    @Inject
    lateinit var movieModel: MovieViewModel

    @Inject
    lateinit var catalogModel: CatalogViewModel

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var appRouter: AppRouter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list)
        mode = intent.getStringExtra(MovieConst.TYPE.toString())
        catalogId = intent.getIntExtra(MovieConst.CATALOG_ID.toString(), -1)

        DaggerAppComponent.builder()
            .appModule(AppModule(application, this))
            .build()
            .inject(this)

        Log.d(TAG, "type = $mode")

        binding.tabSection.setIcon(resources.getDrawable(R.drawable.ic_movie_filter_white_34dp, null))
        initialiseSearch()

        when (mode) {
            BuildConfig.POPULAR -> {
                movies = movieModel.getPopular()
                binding.tabSection.setText(resources.getString(R.string.bt_main_popular))
            }
            BuildConfig.OTHER -> {
                movies = movieModel.getFavorite(catalogId)
                getCatalogName(catalogId)
            }
            else -> {
                Log.e(TAG, "Invalid parameter 'type' = $mode")
                return
            }
        }

        movies.observe(this,
            Observer { displayMovies(it) }
        )

        disposable.addAll(
            favoriteListener.subscribe { showCatalogs(it.first, it.second) },
            movieDetailsListener.subscribe { appRouter.navigateToMovieDetails(it, mode) },
            removeMovieListener.subscribe { removeMovie(it) }
        )

        movieAdapter =
            MovieListAdapter(
                this,
                mode,
                movieModel,
                imageLoader,
                { favoriteListener.onNext(it) },
                { movieDetailsListener.onNext(it) },
                { removeMovieListener.onNext(it) }
            )
        binding.rvItems.layoutManager = LinearLayoutManager(this)
        binding.rvItems.adapter = movieAdapter
    }

    private fun displayMovies(movies: PagedList<Movie>) {
        binding.emptyState.showEmptyState(movies.isEmpty())
        binding.emptyStateStar.setOnClickListener { appRouter.navigateToPopular() }

        movieAdapter.submitList(movies)
    }

    private fun getCatalogName(catalogId: Int) {
        catalogModel.getCatalogName(catalogId)
            .observe( this,
                Observer {
                    binding.tabSection.setText(it)
                })
    }

    private fun initialiseSearch() {
        binding.search.isActivated = true
        binding.search.onActionViewExpanded()
        binding.search.queryHint = getString(R.string.search)
        binding.search.isIconified = false
        binding.search.clearFocus()
        binding.search.isFocusable = false

        binding.search.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isBlank())
                    restoreMovies()
                else
                    initSearch(newText)

                return false
            }
        })
    }

    private fun restoreMovies() {
        movies.observe(this,
            Observer { movieAdapter.submitList(it) }
        )
    }

    private fun initSearch(query: String) {
        val searchMovies = when (mode) {
            BuildConfig.POPULAR -> movieModel.cloudSearch(query)
            BuildConfig.OTHER -> movieModel.getFavorite(catalogId, query)
            else -> {
                Log.e(TAG, "Invalid parameter 'type' = $mode")
                return
            }
        }

        searchMovies.observe(this,
            Observer {
                movieAdapter.submitList(it)
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    private fun showCatalogs(movie: Movie, position: Int) {
        val data = catalogModel.getMovieAndAvailableCatalogs(movie.id)
        data.observe(this, object : Observer<Pair<Optional<Movie>, List<Catalog>>> {
            var movieFull = movie
            override fun onChanged(pair: Pair<Optional<Movie>, List<Catalog>>) {
                if (pair.first.isPresent) movieFull = pair.first.get()

                if (pair.second.isEmpty()) {
                    showMessage(this@MovieListActivity, R.string.no_available_catalog)
                } else {
                    showDialog(movieFull, position, pair.second)
                }

                data.removeObserver(this)
            }
        })
    }

    private fun showDialog(
        movie: Movie,
        position: Int,
        catalogs: List<Catalog>
    ) {
        val multiItems = catalogs.map { it.title }.toTypedArray()
        val checkedItems = multiItems.map { false }
        val checkedIds = mutableSetOf<Int>()

        //create dialog
        val dialog =
            MaterialAlertDialogBuilder(
                this,
                R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog
            )
                .setCancelable(true)
                .setTitle(getString(R.string.choose_catalog))
                .setMultiChoiceItems(
                    multiItems,
                    checkedItems.toBooleanArray()
                ) { _, which, checked ->
                    catalogs[which].id?.let {
                        if (checked) checkedIds.add(it) else checkedIds.remove(it)
                        isCheckedListener.onNext(checkedIds.isNotEmpty())
                    }
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog: DialogInterface, _: Int ->
                    dialog.cancel()
                }
                .setPositiveButton(getString(R.string.ok), null)
                .create()

        dialog.setOnShowListener {
            val button = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            button.isEnabled = false
            isCheckedListener.subscribe { button.isEnabled = it }
            button.setOnClickListener {
                saveMovie(movie, checkedIds, position)
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun saveMovie(movie: Movie, newIds: Set<Int>, position: Int) {
        disposable.add(
            movieModel.saveFavorite(SaveMovieRequest(movie, newIds))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    movieAdapter.notifyItemChanged(position)
                    showMessage(this, movie.title + " " + getString(R.string.added_to_catalog))
                }
        )
    }

    private fun removeMovie(movie: Movie) {
        disposable.add(
            movieModel.removeMovie(
                RemoveMovieRequest(
                    movie,
                    catalogId
                )
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { showMessage(this, movie.title + " " + getString(R.string.is_removed)) }
        )
    }

    companion object {
        private val TAG = MovieListActivity::class.java.simpleName
    }
}