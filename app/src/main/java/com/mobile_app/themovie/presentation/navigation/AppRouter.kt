package com.mobile_app.themovie.presentation.navigation

import android.content.Context
import android.content.Intent
import com.mobile_app.themovie.BuildConfig
import com.mobile_app.themovie.presentation.ui.movie_details.MovieDetailsActivity
import com.mobile_app.themovie.presentation.ui.movie_list.MovieListActivity
import com.mobile_app.themovie.presentation.util.MovieConst
import com.mobile_app.themovie.presentation.ui.movie_details.video.YouTubePlayerActivity

class AppRouter(private val context: Context) {

    fun navigateToPopular() {
        val intent = Intent(context, MovieListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(MovieConst.TYPE.toString(), BuildConfig.POPULAR)
        context.startActivity(intent)
    }

    fun navigateToMovieDetails(movieId: Int, mode: String) {
        val intent = Intent(context, MovieDetailsActivity::class.java)
        intent.putExtra(MovieConst.MOVIE_ID.toString(), movieId)
        intent.putExtra(MovieConst.TYPE.toString(), mode)
        context.startActivity(intent)
    }

    fun navigateToCatalog(catalogId: Int) {
        val intent = Intent(context, MovieListActivity::class.java)
        intent.putExtra(MovieConst.TYPE.toString(), BuildConfig.OTHER)
        intent.putExtra(MovieConst.CATALOG_ID.toString(), catalogId)
        context.startActivity(intent)
    }

    fun navigateToYouTubePlayer(videoCode: String) {
        val intent = Intent(context, YouTubePlayerActivity::class.java)
        intent.putExtra(MovieConst.VIDEO_CODE.toString(), videoCode)
        context.startActivity(intent)
    }
}