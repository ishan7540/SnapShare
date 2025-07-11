package com.example.galleryapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.galleryapp.databinding.ActivityHomeScreenBinding
import com.example.galleryapp.fragments.FavoriteFragment
import com.example.galleryapp.fragments.PhotosFragment
import com.example.galleryapp.fragments.ProfileFragment
import com.example.galleryapp.viewModel.PhotosViewModel
import com.example.galleryapp.adapters.RvAdapter

class HomeScreen : AppCompatActivity() {
    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var adapter: RvAdapter
    private lateinit var viewModel: PhotosViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[PhotosViewModel::class.java]
        replaceFragment(FavoriteFragment())
        binding.heartCount.text = viewModel.favCount.toString()
        binding.navigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.photos -> replaceFragment(PhotosFragment())
                R.id.favorite -> replaceFragment(FavoriteFragment())
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fv, fragment)
        transaction.commit()
    }

    fun updateCount() {
        binding.heartCount.text = viewModel.favCount.toString()
    }

}
