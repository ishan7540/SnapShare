package com.example.galleryapp.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.AndroidViewModel
import com.example.galleryapp.RetrofitInstance
import com.example.galleryapp.models.PhotosItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosViewModel(application: Application) : AndroidViewModel(application) {

    val sharedPreferences by lazy {
        application.getSharedPreferences("gallery_prefs", Context.MODE_PRIVATE)
    }

    private val gson = Gson()

    var favoritePhotos: ArrayList<PhotosItem> = java.util.ArrayList()
    var favCount = favoritePhotos.size


    fun getData(
        onResult: (List<PhotosItem>) -> Unit,
        onError: () -> Unit
    ) {
        val call = RetrofitInstance.apiInterface.getData()
        call.enqueue(object : Callback<List<PhotosItem>> {
            override fun onResponse(call: Call<List<PhotosItem>>, response: Response<List<PhotosItem>>) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d("favphotos",response.body().toString())


                    val mList = ArrayList<PhotosItem>()

                    response.body()?.forEach { item->
                        if (favoritePhotos.firstOrNull { it.download_url  == item.download_url}!=null){
                            item.isFavorite = true
                        }
                        mList.add(item)
                        Log.d("getting in mList",mList.toString())
                    }
                    onResult(mList)
                } else {
                    //Toast.makeText(context, "No data found", LENGTH_SHORT).show()
                    onError()
                }
            }

            override fun onFailure(call: Call<List<PhotosItem>>, t: Throwable) {
                //Toast.makeText(context, "API call failed", LENGTH_SHORT).show()
                onError()
            }
        })
    }



    fun removeFavorite(photo: PhotosItem) {
        favoritePhotos.removeIf { it.download_url == photo.download_url }
        saveFavorites()
        updateFavCount()
    }

    fun updateFavCount() {
        favCount = favoritePhotos.size
    }

    fun getFavorites(): ArrayList<PhotosItem> {
        Log.d("checkvalues",favoritePhotos.toString())
        return favoritePhotos
    }

    fun saveFavorites() {
        val json = gson.toJson(favoritePhotos)
        Log.d("gettingsaved",json.toString())
        sharedPreferences.edit().putString("favorites_list", json).apply()
    }

    fun loadFavorites() {
        val json = sharedPreferences.getString("favorites_list", null)
        if (json != null) {
            val type = object : TypeToken<ArrayList<PhotosItem>>() {}.type
            favoritePhotos.clear()
            favoritePhotos.addAll(gson.fromJson<ArrayList<PhotosItem>>(json, type))
        }
    }
}
