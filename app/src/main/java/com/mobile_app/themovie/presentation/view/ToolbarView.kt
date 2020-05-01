package com.mobile_app.themovie.presentation.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.mobile_app.themovie.R
import kotlinx.android.synthetic.main.tab_view.view.*

class TabView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defRes) {

    init {
        LayoutInflater.from(context).inflate(R.layout.tab_view, this)
    }

    fun setText(title: String) {
        tab_name.text = title
    }

    fun setIcon(drawable: Drawable) {
        tab_icon.setImageDrawable(drawable)
    }
}