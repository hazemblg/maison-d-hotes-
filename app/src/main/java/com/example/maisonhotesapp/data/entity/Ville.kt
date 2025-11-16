package com.example.maisonhotes.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "villes",
    foreignKeys = [
        ForeignKey(
            entity = Region::class,
            parentColumns = ["id"],
            childColumns = ["regionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Ville(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nom: String,
    val regionId: Int,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)