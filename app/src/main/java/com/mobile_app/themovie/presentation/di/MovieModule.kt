package com.mobile_app.themovie.presentation.di

import android.content.Context
import com.mobile_app.themovie.data.dao.MovieCloudDAO
import com.mobile_app.themovie.data.dao.MovieDAO
import com.mobile_app.themovie.data.database.MovieDatabase
import com.mobile_app.themovie.data.datasource.MovieDataSource
import com.mobile_app.themovie.data.mapper.AppDatabase
import com.mobile_app.themovie.data.repository.cloud.MovieCloudRepositoryImpl
import com.mobile_app.themovie.data.repository.local.MovieRepositoryImpl
import com.mobile_app.themovie.domain.repository.cloud.MovieCloudRepository
import com.mobile_app.themovie.domain.repository.local.MovieRepository
import com.mobile_app.themovie.domain.usecase.movie.GetMovieUseCase
import com.mobile_app.themovie.domain.usecase.movie.GetMovieUseCaseImpl
import com.mobile_app.themovie.domain.usecase.movie.cloud.*
import com.mobile_app.themovie.domain.usecase.movie.local.*
import com.mobile_app.themovie.presentation.util.DateFormatter
import com.mobile_app.themovie.presentation.util.ImageLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MovieModule {

    // Local
    @Singleton
    @Provides
    fun providesMovieDao(appDatabase: AppDatabase): MovieDAO {
        return appDatabase.movieDao
    }

    @Provides
    @Singleton
    fun provideMovieDataSource(movieDAO: MovieDAO): MovieDataSource {
        return MovieDatabase(movieDAO)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(movieDataSource: MovieDataSource): MovieRepository {
        return MovieRepositoryImpl(movieDataSource)
    }

    @Provides
    @Singleton
    fun provideSaveMovieUseCase(movieRepository: MovieRepository): SaveMovieUseCase {
        return SaveMovieUseCaseImpl(movieRepository)
    }

    @Provides
    @Singleton
    fun provideAllMoviesUseCase(movieRepository: MovieRepository): GetAllMoviesUseCase {
        return GetAllMoviesUseCaseImpl(movieRepository)
    }

    @Provides
    @Singleton
    fun provideGetMovieByIdUseCase(
        movieRepository: MovieRepository,
        movieCloudRepository: MovieCloudRepository
    ): GetMovieUseCase {
        return GetMovieUseCaseImpl(
            movieRepository,
            movieCloudRepository
        )
    }

    @Provides
    @Singleton
    fun provideContainsMovieUseCase(movieRepository: MovieRepository): ContainsMovieUseCase {
        return ContainsMovieUseCaseImpl(movieRepository)
    }

    @Provides
    @Singleton
    fun provideRemoveOrUpdateMovieUseCase(movieRepository: MovieRepository): RemoveOrUpdateMovieUseCase {
        return RemoveOrUpdateMovieUseCaseImpl(movieRepository)
    }

    // Cloud
    @Singleton
    @Provides
    fun providesMovieCloudDao(context: Context): MovieCloudDAO {
        return MovieCloudDAO(context.resources.configuration.locale.language)
    }

    @Provides
    @Singleton
    fun provideMovieCloudRepository(movieCloudDAO: MovieCloudDAO): MovieCloudRepository {
        return MovieCloudRepositoryImpl(movieCloudDAO)
    }

    @Provides
    @Singleton
    fun provideMoviesCloudUseCase(movieCloudRepository: MovieCloudRepository): GetAllMoviesCloudUseCase {
        return GetAllMoviesCloudUseCaseImpl(movieCloudRepository)
    }

    @Provides
    @Singleton
    fun provideSearchMoviesCloudUseCase(movieCloudRepository: MovieCloudRepository): SearchMoviesCloudUseCase {
        return SearchMoviesCloudUseCaseImpl(movieCloudRepository)
    }

    @Provides
    @Singleton
    fun provideVideoUseCase(movieCloudRepository: MovieCloudRepository): GetVideoUseCase {
        return GetVideoUseCaseImpl(movieCloudRepository)
    }

    @Provides
    @Singleton
    fun provideDateFormatter(context: Context) = DateFormatter(context.resources.configuration.locale)

    @Provides
    @Singleton
    fun provideImageLoader(context: Context) = ImageLoader(context)
}