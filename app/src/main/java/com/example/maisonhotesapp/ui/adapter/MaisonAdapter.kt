package com.example.maisonhotesapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.maisonhotesapp.R
import com.example.maisonhotesapp.data.entity.MaisonHote
import com.example.maisonhotesapp.databinding.ItemMaisonBinding

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

    class MaisonViewHolder(
        private val binding: ItemMaisonBinding,
        private val onItemClick: (MaisonHote) -> Unit,
        private val onFavoriteClick: (MaisonHote) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(maison: MaisonHote) {
            binding.nomMaison.text = maison.nom
            binding.prixNuit.text = "${maison.prix}€ / nuit"
            binding.notation.text = String.format("%.1f", maison.notation)
            binding.nombreAvis.text = "(${maison.nombreAvis} avis)"

            // Charger l'image avec Glide
            val imageResId = if (maison.imageUrl.isNotEmpty() && !maison.imageUrl.startsWith("http")) {
                // Si c'est un nom de ressource locale (ex: "maison_tunis_1")
                val resId = itemView.context.resources.getIdentifier(
                    maison.imageUrl,
                    "drawable",
                    itemView.context.packageName
                )
                if (resId != 0) resId else R.drawable.ic_placeholder
            } else if (maison.imageUrl.startsWith("http")) {
                // Si c'est une URL complète
                maison.imageUrl
            } else {
                R.drawable.ic_placeholder
            }

            Glide.with(itemView.context)
                .load(imageResId)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(binding.imageMaison)

            // Icône favori
            binding.favoriteBtn.setIconResource(
                if (maison.isFavorite) R.drawable.ic_favorite_filled
                else R.drawable.ic_favorite_empty
            )

            // Clics
            itemView.setOnClickListener { onItemClick(maison) }
            binding.favoriteBtn.setOnClickListener { onFavoriteClick(maison) }
        }
    }

    class MaisonDiffCallback : DiffUtil.ItemCallback<MaisonHote>() {
        override fun areItemsTheSame(oldItem: MaisonHote, newItem: MaisonHote) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MaisonHote, newItem: MaisonHote) =
            oldItem == newItem
    }
}