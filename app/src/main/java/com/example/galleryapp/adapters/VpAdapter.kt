package com.example.galleryapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.galleryapp.fragments.AboutFragment
import com.example.galleryapp.fragments.AboutGalleryFragment
import com.example.galleryapp.fragments.GalleryStatsFragment

class VpAdapter(activity: Fragment) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AboutFragment()
            1 -> GalleryStatsFragment()
            2 -> AboutGalleryFragment()
            else -> {AboutFragment()}
        }
    }
}