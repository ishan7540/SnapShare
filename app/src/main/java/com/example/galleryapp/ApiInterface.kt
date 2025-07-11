package com.example.galleryapp

import com.example.galleryapp.models.PhotosItem
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("list")
    fun getData(): Call<List<PhotosItem>>

}