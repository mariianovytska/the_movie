package com.mobile_app.themovie.data.mapper;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mobile_app.themovie.data.converter.CatalogIdsConverter;
import com.mobile_app.themovie.data.dao.CatalogDAO;
import com.mobile_app.themovie.data.dao.MovieDAO;
import com.mobile_app.themovie.data.entity.Catalog;
import com.mobile_app.themovie.data.entity.Movie;

@Database(
        entities = {Movie.class, Catalog.class},
        version = AppDatabase.VERSION,
        exportSchema = false
)
@TypeConverters(CatalogIdsConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    static final int VERSION = 2;

    public abstract CatalogDAO getCatalogDao();
    public abstract MovieDAO getMovieDao();
}
