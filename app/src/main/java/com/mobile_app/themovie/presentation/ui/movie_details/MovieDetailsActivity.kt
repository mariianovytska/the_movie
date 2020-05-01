package com.mobile_app.themovie.presentation.ui.movie_details

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile_app.themovie.BuildConfig
import com.mobile_app.themovie.R
import com.mobile_app.themovie.data.entity.Catalog
import com.mobile_app.themovie.data.entity.Movie
import com.mobile_app.themovie.data.entity.Video
import com.mobile_app.themovie.databinding.ActivityMovieDetailsBinding
import com.mobile_app.themovie.domain.entity.MovieRequest
import com.mobile_app.themovie.presentation.di.AppModule
import com.mobile_app.themovie.presentation.di.DaggerAppComponent
import com.mobile_app.themovie.presentation.navigation.AppRouter
import com.mobile_app.themovie.presentation.ui.movie_details.video.ThumbnailListAdapter
import com.mobile_app.themovie.presentation.ui.view_model.CatalogViewModel
import com.mobile_app.themovie.presentation.ui.view_model.MovieViewModel
import com.mobile_app.themovie.presentation.util.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.*
import javax.inject.Inject

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding
    private val disposable = CompositeDisposable()
    private val videoClickListener = PublishSubject.create<String>()

    @Inject
    lateinit var movieModel: MovieViewModel

    @Inject
    lateinit var catalogModel: CatalogViewModel

    @Inject
    lateinit var dateFormatter: DateFormatter

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var appRouter: AppRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)

        val movieId = intent.getIntExtra(MovieConst.MOVIE_ID.toString(), 0)
        val mode = intent.getStringExtra(MovieConst.TYPE.toString())

        DaggerAppComponent.builder()
            .appModule(AppModule(application, this))
            .build()
            .inject(this)

        movieModel.getMovie(
            MovieRequest(mode, movieId)
        ).observe(this,
            Observer { movie ->
                if (movie.isPresent) {
                    loadCatalogs(movie.get())
                }
            }
        )

        //Trailers
        disposable.addAll(
            movieModel.getVideo(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        setTrailers(it.filter {
                            it.site == MovieConst.YOUTUBE.toString()
                        })
                    },
                    { showMessage(this, R.string.no_network_connection) }
                ),

            videoClickListener.subscribe { appRouter.navigateToYouTubePlayer(it) }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    private fun setTrailers(videos: List<Video>) {
        val adapter = ThumbnailListAdapter(this, videos, appRouter)
        binding.rvVideo.setHasFixedSize(true)
        binding.rvVideo.adapter = adapter
        binding.rvVideo.layoutManager = LinearLayoutManager(this)
        binding.rvVideo.layoutManager
    }

    private fun loadCatalogs(movie: Movie) {
        val data = catalogModel.getMovieCatalogs(movie.id)
        data.observe(this, object : Observer<List<Catalog>> {
            override fun onChanged(catalogs: List<Catalog>) {
                initialiseMovie(
                    movie,
                    catalogs.map { it.title })
                data.removeObserver(this)
            }
        })
    }

    private fun initialiseMovie(value: Movie, catalogNames: List<String>) {
        binding.vCatalogSection.clear()
        binding.tabSection.setText(value.title)
        binding.tabSection.setIcon(resources.getDrawable(R.drawable.ic_local_play_white_34dp, null))

        value.posterPath?.let {
            binding.adIvPoster.scaleType = ImageView.ScaleType.FIT_CENTER
            imageLoader.loadImage(
                BuildConfig.IMAGE_URL + it,
                binding.adIvPoster
            )
        } ?: run {
            binding.adIvPoster.setImageResource(R.drawable.icon_no_image)
            binding.adIvPoster.scaleType = ImageView.ScaleType.CENTER_INSIDE
        }

        binding.adTvTitle.text = value.originalTitle
        binding.adTvOverview.text = value.overview.makeIndent()

        binding.adTvRelease.text = getString(R.string.release)
        binding.adTvReleaseText.text = dateFormatter.convertFormat(value.releaseDate)

        binding.adTvVote.text = getString(R.string.rating)
        binding.adTvVoteText.text = value.voteAverage.toString()

        binding.adTvLanguage.text = getString(R.string.language)
        binding.adTvLanguageText.text = Locale(value.originalLanguage).displayLanguage

        catalogNames.forEach {
            binding.vCatalogSection.addChip(it)
        }
    }
}
