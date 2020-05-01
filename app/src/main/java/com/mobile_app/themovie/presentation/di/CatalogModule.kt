package com.mobile_app.themovie.presentation.di

import com.mobile_app.themovie.data.dao.CatalogDAO
import com.mobile_app.themovie.data.database.CatalogDatabase
import com.mobile_app.themovie.data.datasource.CatalogDataSource
import com.mobile_app.themovie.data.mapper.AppDatabase
import com.mobile_app.themovie.data.repository.local.CatalogRepositoryImpl
import com.mobile_app.themovie.domain.repository.local.CatalogRepository
import com.mobile_app.themovie.domain.repository.local.MovieRepository
import com.mobile_app.themovie.domain.usecase.catalog.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CatalogModule {
    @Provides
    @Singleton
    fun provideCatalogDAO(appDatabase: AppDatabase): CatalogDAO {
        return appDatabase.catalogDao
    }

    @Provides
    @Singleton
    fun provideCatalogDataSource(catalogDAO: CatalogDAO): CatalogDataSource {
        return CatalogDatabase(catalogDAO)
    }

    @Provides
    @Singleton
    fun provideCatalogRepository(catalogDataSource: CatalogDataSource): CatalogRepository {
        return CatalogRepositoryImpl(catalogDataSource)
    }

    @Provides
    @Singleton
    fun provideSaveCatalogUseCase(catalogRepository: CatalogRepository): SaveCatalogUseCase {
        return SaveCatalogUseCaseImpl(catalogRepository)
    }

    @Provides
    @Singleton
    fun provideAllCatalogsUseCase(catalogRepository: CatalogRepository): GetAllCatalogsUseCase {
        return GetAllCatalogsUseCaseImpl(catalogRepository)
    }

    @Provides
    @Singleton
    fun provideGetMovieCatalogsUseCase(
        movieRepository: MovieRepository,
        catalogRepository: CatalogRepository
    ): GetMovieCatalogsUseCase {
        return GetMovieCatalogsUseCaseImpl(movieRepository, catalogRepository)
    }

    @Provides
    @Singleton
    fun provideGetMovieAndAvailableCatalogsUseCase(
        movieRepository: MovieRepository,
        catalogRepository: CatalogRepository
    ): GetMovieAndAvailableCatalogsUseCase {
        return GetMovieAndAvailableCatalogsUseCaseImpl(movieRepository, catalogRepository)
    }

    @Provides
    @Singleton
    fun provideRemoveCatalogUseCase(
        catalogRepository: CatalogRepository,
        movieRepository: MovieRepository
    ): RemoveCatalogWithMoviesUseCase {
        return RemoveCatalogWithMoviesUseCaseImpl(catalogRepository, movieRepository)
    }

    @Provides
    @Singleton
    fun provideCatalogNameUseCase(catalogRepository: CatalogRepository): GetCatalogNameUseCase {
        return GetCatalogNameUseCaseImpl(catalogRepository)
    }
}