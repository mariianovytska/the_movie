package com.mobile_app.themovie.presentation.ui.movie_list.paged

import androidx.recyclerview.widget.DiffUtil
import com.mobile_app.themovie.data.entity.Movie

class MovieDiffutilCallback: DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}