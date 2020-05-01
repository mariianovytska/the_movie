package com.mobile_app.themovie.domain.entity

import androidx.lifecycle.LifecycleOwner

data class RemoveCatalogRequest(val id: Int, val lifecycleOwner: LifecycleOwner)