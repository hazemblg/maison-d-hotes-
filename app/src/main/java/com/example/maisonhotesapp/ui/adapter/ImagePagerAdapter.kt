package com.example.maisonhotes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.maisonhotes.R
import com.example.maisonhotes.data.entity.Image
import com.example.maisonhotes.databinding.ItemImageBinding

class ImagePagerAdapter(private val images: List<Image>) :
    RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount() = images.size

    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Image) {
            Glide.with(binding.root.context)
                .load(image.url)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.imageView)
        }
    }
}