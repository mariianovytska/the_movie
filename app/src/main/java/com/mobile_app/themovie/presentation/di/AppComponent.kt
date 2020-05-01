package com.mobile_app.themovie.presentation.di

import com.mobile_app.themovie.presentation.ui.catalog_list.CatalogListActivity
import com.mobile_app.themovie.presentation.ui.movie_details.MovieDetailsActivity
import com.mobile_app.themovie.presentation.ui.movie_list.MovieListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, MovieModule::class, CatalogModule::class])
interface AppComponent {
    fun inject(catalogListActivity: CatalogListActivity?)
    fun inject(movieListActivity: MovieListActivity?)
    fun inject(movieDetailsActivity: MovieDetailsActivity?)
}