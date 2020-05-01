package com.mobile_app.themovie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "catalog")
data class Catalog (

    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "title") var title: String = ""
)