package com.mobile_app.themovie.presentation.util

import android.content.Context
import android.widget.Toast

fun showMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG)
        .show()
}

fun showMessage(context: Context, resId: Int) {
    Toast.makeText(context, context.getString(resId), Toast.LENGTH_LONG)
        .show()
}