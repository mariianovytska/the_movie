package com.mobile_app.themovie.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VideoResponse<T>(

    @SerializedName("id") val id: Int = 0,
    @SerializedName("results") val results: List<Video> = listOf()
): Serializable