package com.example.maisonhotes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.maisonhotes.data.entity.Avis
import com.example.maisonhotes.databinding.ItemAvisBinding
import java.text.SimpleDateFormat
import java.util.*

class AvisAdapter : ListAdapter<Avis, AvisAdapter.AvisViewHolder>(AvisDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvisViewHolder {
        val binding = ItemAvisBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AvisViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AvisViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AvisViewHolder(private val binding: ItemAvisBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(avis: Avis) {
            binding.apply {
                auteur.text = avis.auteur
                contenu.text = avis.contenu
                notation.text = String.format("%.1f", avis.notation)

                // Formater la date
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("fr", "FR"))
                dateAvis.text = dateFormat.format(Date(avis.dateAvis))

                // Rating bar
                ratingBar.rating = avis.notation
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