package com.mobile_app.themovie.domain.entity

import com.mobile_app.themovie.data.entity.Movie

data class SaveMovieRequest(val movie: Movie, val catalogIds: Set<Int>)