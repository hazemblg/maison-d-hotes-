package com.example.maisonhotesapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.maisonhotesapp.data.entity.Avis
import com.example.maisonhotesapp.databinding.ItemAvisBinding

class AvisAdapter : ListAdapter<Avis, AvisAdapter.AvisViewHolder>(AvisDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvisViewHolder {
        val binding = ItemAvisBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AvisViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AvisViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AvisViewHolder(private val binding: ItemAvisBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(avis: Avis) {
            binding.apply {
                auteur.text = avis.auteur
                contenu.text = avis.contenu
                notation.text = String.format("%.1f", avis.note)

                // Date (déjà formatée en String)
                dateAvis.text = avis.dateAvis

                // Rating bar
                ratingBar.rating = avis.note
            }
        }
    }

    class AvisDiffCallback : DiffUtil.ItemCallback<Avis>() {
        override fun areItemsTheSame(oldItem: Avis, newItem: Avis) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Avis, newItem: Avis) =
            oldItem == newItem
    }
}