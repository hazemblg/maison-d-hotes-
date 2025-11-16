package com.example.maisonhotes.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "regions")
data class Region(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nom: String,
    val description: String = ""
)