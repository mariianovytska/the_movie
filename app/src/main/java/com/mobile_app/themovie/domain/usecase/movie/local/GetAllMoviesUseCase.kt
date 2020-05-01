package com.mobile_app.themovie.domain.usecase.movie.local

import androidx.paging.PagedList
import com.mobile_app.themovie.data.entity.Movie
import com.mobile_app.themovie.domain.entity.AllMovieRequest
import com.mobile_app.themovie.domain.usecase.BaseUseCases

abstract class GetAllMoviesUseCase : BaseUseCases.LiveDataUseCase<AllMovieRequest, PagedList<Movie>>()