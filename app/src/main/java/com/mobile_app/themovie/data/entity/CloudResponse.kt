package com.mobile_app.themovie.data.entity

import com.google.gson.annotations.SerializedName

data class CloudResponse<T>(

    @SerializedName("page") var page: Int = 0,
    @SerializedName("results") val results: List<T> = listOf(),
    @SerializedName("total_results") val totalResults: Int = 0,
    @SerializedName("total_pages") val totalPages: Int = 0
)