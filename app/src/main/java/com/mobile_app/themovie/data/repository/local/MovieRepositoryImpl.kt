package com.mobile_app.themovie.data.repository.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.common.base.Optional
import com.mobile_app.themovie.data.datasource.MovieDataSource
import com.mobile_app.themovie.data.entity.Movie
import com.mobile_app.themovie.domain.repository.local.MovieRepository
import io.reactivex.Completable
import java.util.TreeSet


class MovieRepositoryImpl(private val movieDataSource: MovieDataSource) :
    MovieRepository {

    override fun save(movie: Movie): Completable {
        return movieDataSource.save(movie)
    }

    override fun get(id: Int): LiveData<Optional<Movie>> {
        return movieDataSource.get(id)
    }

    override fun getAll(): LiveData<List<Movie>> {
        return movieDataSource.getAll()
    }

    override fun contains(id: Int): LiveData<Boolean> {
        return Transformations.map(movieDataSource.get(id))
        { it.isPresent }
    }

    override fun delete(id: Int): Completable {
        return movieDataSource.delete(id)
    }

    override fun deleteOrUpdate(movie: Movie, catalogId: Int): Completable {
        val catalogIds: MutableSet<Int> = TreeSet()
        movie.catalogIds?.let {
            catalogIds.addAll(it)
            catalogIds.remove(catalogId)

            movie.catalogIds = catalogIds
            return if (catalogIds.isEmpty()) {
                delete(movie.id)
            } else {
                save(movie)
            }
        } ?: return Completable.error(IllegalArgumentException("CatalogIds can`t be null"))
    }

}
