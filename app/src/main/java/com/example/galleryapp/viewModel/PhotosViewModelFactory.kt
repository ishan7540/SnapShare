package com.example.galleryapp.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PhotosViewModelFactory(val app:Application):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PhotosViewModel::class.java)){
                return PhotosViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
    }
}