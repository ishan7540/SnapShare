package com.example.galleryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galleryapp.R
import com.example.galleryapp.databinding.ItemBinding
import com.example.galleryapp.models.PhotosItem

class RvAdapter(
    private val urlList: ArrayList<PhotosItem>,
    private val onFavoriteClick: (Int) -> Unit,
) : RecyclerView.Adapter<RvAdapter.RvViewHolder>() {
    private lateinit var curr_fav: ArrayList<PhotosItem>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        holder.bind(urlList[position])
    }

    override fun getItemCount(): Int = urlList.size

    inner class RvViewHolder(private val binding: ItemBinding) :
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
                onFavoriteClick(absoluteAdapterPosition)
            }
        }
    }
}
