package com.mobile_app.themovie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity(tableName = "movie")
data class Movie(

    @ColumnInfo(name = "poster_path") @SerializedName("poster_path")
    var posterPath: String? = "",

    @ColumnInfo(name = "overview") @SerializedName("overview")
    val overview: String = "",

    @ColumnInfo(name = "release_date") @SerializedName("release_date")
    val releaseDate: String = "",

    @PrimaryKey @SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "original_language") @SerializedName("original_language")
    val originalLanguage: String = "",

    @ColumnInfo(name = "title") @SerializedName("title")
    val title: String = "",

    @ColumnInfo(name = "vote_average") @SerializedName("vote_average")
    val voteAverage: Double = 0.0,

    @ColumnInfo(name = "original_title") @SerializedName("original_title")
    val originalTitle: String = "",

    @ColumnInfo(name = "catalog_ids")
    var catalogIds: Set<Int>? = TreeSet()

) : Serializable