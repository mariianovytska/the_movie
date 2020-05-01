package com.mobile_app.themovie.presentation.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mobile_app.themovie.data.mapper.AppDatabase
import com.mobile_app.themovie.presentation.navigation.AppRouter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(
    private val mApplication: Application,
    private val context: Context
) {
    private val appDatabase: AppDatabase

    private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE movie "
                    + " ADD COLUMN original_title TEXT NOT NULL DEFAULT ''")
        }
    }

    init {
        appDatabase = Room.databaseBuilder(
            mApplication,
            AppDatabase::class.java,
            "movie-db"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun providesContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun providesApplication(): Application {
        return mApplication
    }

    @Singleton
    @Provides
    fun providesAppDatabase(): AppDatabase {
        return appDatabase
    }

    @Singleton
    @Provides
    fun providesAppRouter(context: Context) =
        AppRouter(context)
}