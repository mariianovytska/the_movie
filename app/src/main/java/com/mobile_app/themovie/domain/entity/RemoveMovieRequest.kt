package com.mobile_app.themovie.domain.entity

import com.mobile_app.themovie.data.entity.Movie

data class RemoveMovieRequest(val movie: Movie, val catalogId: Int)