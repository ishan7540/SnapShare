package com.example.galleryapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galleryapp.HomeScreen
import com.example.galleryapp.viewModel.PhotosViewModel
import com.example.galleryapp.adapters.FavRvAdapter
import com.example.galleryapp.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: PhotosViewModel
    private lateinit var adapter: FavRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[PhotosViewModel::class.java]
        viewModel.loadFavorites()
        val favList = viewModel.getFavorites()

        adapter = FavRvAdapter(favList) { photo ->
            viewModel.removeFavorite(photo)
            adapter.notifyDataSetChanged()
            (activity as? HomeScreen)?.updateCount()
        }

        binding.rvFav.layoutManager = GridLayoutManager(activity, 2)
        binding.rvFav.adapter = adapter

        return binding.root
    }
}
