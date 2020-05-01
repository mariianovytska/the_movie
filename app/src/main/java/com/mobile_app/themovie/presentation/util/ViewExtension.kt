package com.mobile_app.themovie.presentation.util

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

fun TextView.showEmptyState(isEmpty: Boolean) {
    this.visibility =
        if (isEmpty) View.VISIBLE else View.GONE
}

fun LinearLayout.showEmptyState(isEmpty: Boolean) {
    this.visibility =
        if (isEmpty) View.VISIBLE else View.GONE
}

fun ImageView.show() {
    this.visibility = View.VISIBLE
}

fun ImageView.hide() {
    this.visibility = View.GONE
}

fun ImageView.isVisible() =
    this.visibility == View.VISIBLE
