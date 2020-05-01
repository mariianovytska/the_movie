package com.mobile_app.themovie.presentation.util

fun String.makeIndent() = "\u3000\u3000".plus(this.trim())
fun String.makeHash() = "# ".plus(this)