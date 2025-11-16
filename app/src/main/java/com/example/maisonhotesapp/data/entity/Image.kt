package com.example.maisonhotes.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "images",
    foreignKeys = [
        ForeignKey(
            entity = MaisonHote::class,
            parentColumns = ["id"],
            childColumns = ["maisonHoteId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Image(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val maisonHoteId: Int,
    val url: String,
    val ordreAffichage: Int = 0
)