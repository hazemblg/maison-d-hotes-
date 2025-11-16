package com.example.maisonhotes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.maisonhotes.R
import com.example.maisonhotes.data.entity.MaisonHote
import com.example.maisonhotes.databinding.ItemMaisonBinding

class MaisonAdapter(
    private val onItemClick: (MaisonHote) -> Unit,
    private val onFavoriteClick: (MaisonHote) -> Unit
) : ListAdapter<MaisonHote, MaisonAdapter.MaisonViewHolder>(MaisonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaisonViewHolder {
        val binding = ItemMaisonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MaisonViewHolder(binding, onItemClick, onFavoriteClick)
    }

    override fun onBindViewHolder(holder: MaisonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MaisonViewHolder(
        private val binding: ItemMaisonBinding,
        private val onItemClick: (MaisonHote) -> Unit,
        private val onFavoriteClick: (MaisonHote) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(maison: MaisonHote) {
            binding.apply {
                nomMaison.text = maison.nom
                prixNuit.text = "${maison.prixParNuit}€ / nuit"
                notation.text = String.format("%.1f", maison.notation)
                nombreAvis.text = "${maison.nombreAvis} avis"

                // Charger l'image avec Glide
                Glide.with(binding.root.context)
                    .load(maison.imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_placeholder)
                    .into(imageMaison)

                // Icône favori
                favoriteBtn.setIconResource(
                    if (maison.estFavorite) R.drawable.ic_favorite_filled
                    else R.drawable.ic_favorite_empty
                )

                // Clics
                root.setOnClickListener { onItemClick(maison) }
                favoriteBtn.setOnClickListener { onFavoriteClick(maison) }
            }
        }
    }

    class MaisonDiffCallback : DiffUtil.ItemCallback<MaisonHote>() {
        override fun areItemsTheSame(oldItem: MaisonHote, newItem: MaisonHote) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MaisonHote, newItem: MaisonHote) =
            oldItem == newItem
    }
}