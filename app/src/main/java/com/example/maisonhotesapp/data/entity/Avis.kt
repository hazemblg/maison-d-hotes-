package com.example.maisonhotes.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.maisonhotes.data.entity.MaisonHote

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
    val contenu: String,
    val notation: Float,
    val dateAvis: Long = System.currentTimeMillis()
)