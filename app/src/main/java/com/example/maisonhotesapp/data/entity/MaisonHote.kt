package com.example.maisonhotes.data.entity

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
    val adresse: String,
    val latitude: Double,
    val longitude: Double,
    val prixParNuit: Double,
    val telephone: String,
    val email: String,
    val imageUrl: String,
    val facebook: String = "",
    val instagram: String = "",
    val siteWeb: String = "",
    val notation: Float = 0f,
    val nombreAvis: Int = 0,
    val estFavorite: Boolean = false,
    val capaciteMax: Int,
    val chambres: Int,
    val sallesDeBain: Int,
    val amenites: String = "",
    val dateCreation: Long = System.currentTimeMillis()
)