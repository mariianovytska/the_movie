package com.mobile_app.themovie.presentation.ui.movie_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile_app.themovie.BuildConfig
import com.mobile_app.themovie.R
import com.mobile_app.themovie.data.entity.Movie
import com.mobile_app.themovie.presentation.ui.movie_list.paged.MovieDiffutilCallback
import com.mobile_app.themovie.presentation.ui.view_model.MovieViewModel
import com.mobile_app.themovie.presentation.util.ImageLoader
import com.mobile_app.themovie.presentation.util.MovieConst
import com.mobile_app.themovie.presentation.util.isVisible
import com.mobile_app.themovie.presentation.util.hide
import com.mobile_app.themovie.presentation.util.show

open class MovieListAdapter(
    private val context: Context,
    private val mode: String,
    private val movieModel: MovieViewModel,
    private val imageLoader: ImageLoader,
    private val favoriteListener: (Pair<Movie, Int>) -> Unit,
    private val movieDetailsListener: (Int) -> Unit,
    private val removeMovieListener: (Movie) -> Unit
) : PagedListAdapter<Movie, MovieListAdapter.MovieViewHolder>(MovieDiffutilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            val itemView = holder.itemView

            val image: ImageView =
                itemView.findViewById(R.id.li_iv_poster)

            movie.posterPath?.let {
                image.scaleType = ImageView.ScaleType.FIT_XY
                imageLoader.loadImage(
                    BuildConfig.IMAGE_URL + it,
                    image
                )
            } ?: run {
                image.setImageResource(R.drawable.icon_no_image)
                image.scaleType = ImageView.ScaleType.CENTER_INSIDE
            }

            val name: TextView = itemView.findViewById(R.id.li_tv_title)
            name.text = movie.title

            val vote: TextView = itemView.findViewById(R.id.li_tv_vote)
            vote.text = context.getString(R.string.rating)

            val voteText: TextView = itemView.findViewById(R.id.li_tv_vote_text)
            voteText.text = movie.voteAverage.toString()

            val bin: ImageView = itemView.findViewById(R.id.remove_bin_movie)
            itemView.setOnClickListener {
                if (bin.isVisible()) {
                    bin.hide()
                } else {
                    movieDetailsListener.invoke(movie.id)
                }
            }

            itemView.setOnLongClickListener {
                if (mode == BuildConfig.OTHER) {
                    bin.show()
                }
                true
            }

            bin.setOnClickListener { removeMovieListener.invoke(movie) }

            val imageFavorite: ImageView = itemView.findViewById(R.id.li_iv_favorite_image)
            val data: LiveData<Boolean> = movieModel.isFavorite(movie.id)

            data.observeForever(object : Observer<Boolean> {
                override fun onChanged(isFavorite: Boolean) {
                    val color = if (isFavorite) R.color.colorAccent else R.color.colorGreyLight
                    imageFavorite.setColorFilter(ContextCompat.getColor(context, color))
                    data.removeObserver(this)
                }
            })

            val imageAddToCatalog: ImageView = itemView.findViewById(R.id.li_iv_add_to_catalog)
            imageAddToCatalog.setOnClickListener {
                favoriteListener.invoke(Pair(movie, position))
            }
        }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}