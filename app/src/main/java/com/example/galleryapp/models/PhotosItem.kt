package com.example.galleryapp.models

import java.io.Serializable

data class PhotosItem(
    val download_url: String,
    var isFavorite: Boolean = false,
    val author: String
):Serializable