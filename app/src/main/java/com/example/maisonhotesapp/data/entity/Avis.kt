package com.example.maisonhotesapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "avis",
    foreignKeys = [
        ForeignKey(
            entity = MaisonHote::class,
            parentColumns = ["id"],
            childColumns = ["maisonHoteId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Avis(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val maisonHoteId: Int,
    val auteur: String,
    val note: Float, // 1-5
    val contenu: String,
    val dateAvis: String = ""
)