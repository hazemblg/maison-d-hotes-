package com.example.maisonhotesapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.maisonhotesapp.R
import com.example.maisonhotesapp.data.entity.Image
import com.example.maisonhotesapp.databinding.ItemImageBinding

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
            val imageResId = if (image.url.isNotEmpty() && !image.url.startsWith("http")) {
                // Si c'est un nom de ressource locale (ex: "maison_tunis_1")
                val resId = binding.root.context.resources.getIdentifier(
                    image.url,
                    "drawable",
                    binding.root.context.packageName
                )
                if (resId != 0) resId else R.drawable.ic_placeholder
            } else if (image.url.startsWith("http")) {
                // Si c'est une URL complète
                image.url
            } else {
                R.drawable.ic_placeholder
            }

            Glide.with(binding.root.context)
                .load(imageResId)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.imageView)
        }
    }
}