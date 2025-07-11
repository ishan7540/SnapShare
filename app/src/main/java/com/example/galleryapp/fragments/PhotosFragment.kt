package com.example.galleryapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galleryapp.HomeScreen
import com.example.galleryapp.viewModel.PhotosViewModel
import com.example.galleryapp.adapters.RvAdapter
import com.example.galleryapp.databinding.FragmentPhotosBinding
import com.example.galleryapp.models.PhotosItem

class PhotosFragment : Fragment() {

    private lateinit var binding: FragmentPhotosBinding
    private val final_urlList = ArrayList<PhotosItem>()
    private var current_fav = ArrayList<PhotosItem>()
    private lateinit var adapter: RvAdapter
    private lateinit var viewModel: PhotosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotosBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[PhotosViewModel::class.java]
        viewModel.loadFavorites()
        getData()
        adapter = RvAdapter(final_urlList) { index ->
            val photo = final_urlList[index]
            if (photo.isFavorite) {
                photo.isFavorite = false
                viewModel.favoritePhotos.removeIf { it.download_url == photo.download_url }
                viewModel.saveFavorites()
            } else {
                photo.isFavorite = true
                viewModel.favoritePhotos.add(photo)
                viewModel.saveFavorites()
            }
            adapter.notifyItemChanged(index)
            (activity as? HomeScreen)?.updateCount()
        }
        binding.rv.layoutManager = GridLayoutManager(activity, 2)
        binding.rv.adapter = adapter

        return binding.root
    }

    private fun getData() {

        binding.loadingbar.visibility = View.VISIBLE
        viewModel.getData(
            onResult = { photos ->
                binding.loadingbar.visibility = View.GONE
                final_urlList.clear()
                final_urlList.addAll(photos)
                adapter.notifyDataSetChanged()
            },
            onError = {
                binding.loadingbar.visibility = View.GONE
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        )
    }
}
