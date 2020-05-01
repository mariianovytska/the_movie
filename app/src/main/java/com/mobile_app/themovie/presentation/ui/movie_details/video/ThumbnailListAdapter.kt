package com.mobile_app.themovie.presentation.ui.movie_details.video

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailLoader.OnThumbnailLoadedListener
import com.google.android.youtube.player.YouTubeThumbnailView
import com.mobile_app.themovie.BuildConfig
import com.mobile_app.themovie.R
import com.mobile_app.themovie.data.entity.Video
import com.mobile_app.themovie.presentation.navigation.AppRouter
import com.mobile_app.themovie.presentation.util.makeHash
import kotlinx.android.synthetic.main.youtube_thumbnail_view.view.*


class ThumbnailListAdapter(
    private val context: Context,
    private val videos: List<Video>,
    private val appRouter: AppRouter
) : RecyclerView.Adapter<ThumbnailListAdapter.VideoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.youtube_thumbnail_view, parent, false)
        return VideoHolder(
            contactView
        )
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        val video = videos[position]

        val onThumbnailLoadedListener: OnThumbnailLoadedListener =
            object : OnThumbnailLoadedListener {
                override fun onThumbnailError(
                    youTubeThumbnailView: YouTubeThumbnailView,
                    errorReason: YouTubeThumbnailLoader.ErrorReason
                ) {
                }

                override fun onThumbnailLoaded(
                    youTubeThumbnailView: YouTubeThumbnailView,
                    s: String
                ) {
                    holder.itemView.trailer_title.text = video.name.makeHash()
                    holder.itemView.play_button.setImageDrawable(
                        context.getDrawable(R.drawable.ic_play_arrow_white_48dp)
                    )
                    holder.itemView.container.visibility = View.VISIBLE
                }
            }
        holder.itemView.youtube_thumbnail.initialize(
            BuildConfig.DEVELOPER_KEY,
            object : YouTubeThumbnailView.OnInitializedListener {
                override fun onInitializationSuccess(
                    youTubeThumbnailView: YouTubeThumbnailView,
                    youTubeThumbnailLoader: YouTubeThumbnailLoader
                ) {
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener)
                    youTubeThumbnailLoader.setVideo(video.key)
                }

                override fun onInitializationFailure(
                    youTubeThumbnailView: YouTubeThumbnailView,
                    youTubeInitializationResult: YouTubeInitializationResult
                ) {
                }
            })

        holder.itemView.setOnClickListener {
            appRouter.navigateToYouTubePlayer(video.key)
        }
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    // stores and recycles views as they are scrolled off screen
    class VideoHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)
}