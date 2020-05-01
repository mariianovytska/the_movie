package com.mobile_app.themovie.presentation.util

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso

class ImageLoader(val context: Context) {

    fun loadImage(
        url: String?,
        target: ImageView
    ) {
        Picasso.with(context)
            .load(url)
            .into(target)
    }
}