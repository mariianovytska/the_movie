package com.mobile_app.themovie.presentation.ui.movie_details.video

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle
import com.google.android.youtube.player.YouTubePlayerView
import com.mobile_app.themovie.BuildConfig
import com.mobile_app.themovie.R
import com.mobile_app.themovie.presentation.util.MovieConst


class YouTubePlayerActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private lateinit var youTubeView: YouTubePlayerView
    private lateinit var videoCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        videoCode = intent.getStringExtra(MovieConst.VIDEO_CODE.toString())

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.youtube_view)
        youTubeView = findViewById(R.id.youtube_view)
        youTubeView.initialize(BuildConfig.DEVELOPER_KEY, this)
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        errorReason: YouTubeInitializationResult
    ) {
        if (errorReason.isUserRecoverableError) {
            errorReason.getErrorDialog(this,
                RECOVERY_DIALOG_REQUEST
            ).show()
        } else {
            val errorMessage: String = java.lang.String.format(
                getString(R.string.error_player), errorReason.toString()
            )
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer, wasRestored: Boolean
    ) {
        if (!wasRestored) {
            player.loadVideo(videoCode)
            player.setPlayerStyle(PlayerStyle.DEFAULT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            youTubePlayerProvider.initialize(BuildConfig.DEVELOPER_KEY, this)
        }
    }

    private val youTubePlayerProvider: YouTubePlayer.Provider
        get() = findViewById<YouTubePlayerView>(R.id.youtube_view)

    companion object {
        private const val RECOVERY_DIALOG_REQUEST = 1
    }
}