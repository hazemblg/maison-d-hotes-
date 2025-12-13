package com.example.maisonhotesapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "maisons_hotes",
    foreignKeys = [
        ForeignKey(
            entity = Ville::class,
            parentColumns = ["id"],
            childColumns = ["villeId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MaisonHote(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nom: String,
    val description: String,
    val villeId: Int,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val prix: Double,
    val notation: Float = 0f,
    val nombreAvis: Int = 0,
    val telephone: String = "",
    val email: String = "",
    val siteWeb: String = "",
    val facebook: String = "",
    val instagram: String = "",
    val nombreChambres: Int = 0,
    val nombreLits: Int = 0,
    val amenities: String = "", // JSON or comma-separated
    val isFavorite: Boolean = false,
    val imageUrl: String = "" // URL de l'image principale
)