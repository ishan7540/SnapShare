package com.example.galleryapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galleryapp.R
import com.example.galleryapp.databinding.ItemBinding
import com.example.galleryapp.models.PhotosItem

class FavRvAdapter(
    private val favList: ArrayList<PhotosItem>,
    private val onUnfavoriteClick: (PhotosItem) -> Unit

) : RecyclerView.Adapter<FavRvAdapter.FavViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(favList[position])
    }

    override fun getItemCount(): Int = favList.size

    inner class FavViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: PhotosItem) {
            Glide.with(binding.root.context)
                .load(photo.download_url)
                .into(binding.img)
            binding.title.text = photo.author
            binding.heart.setImageResource(
                if (photo.isFavorite) R.drawable.red_heart_icon else R.drawable.heart_svgrepo_com__1_
            )
            binding.heart.setOnClickListener {
                photo.isFavorite = !photo.isFavorite
                onUnfavoriteClick(photo)
            }
        }
    }
}
