package com.example.maisonhotes.data.dao

import androidx.room.*
import com.example.maisonhotes.data.entity.Ville
import kotlinx.coroutines.flow.Flow

@Dao
interface VilleDao {

    @Insert
    suspend fun insert(ville: Ville)

    @Update
    suspend fun update(ville: Ville)

    @Delete
    suspend fun delete(ville: Ville)

    @Query("SELECT * FROM villes WHERE regionId = :regionId")
    fun getVillesByRegion(regionId: Int): Flow<List<Ville>>

    @Query("SELECT * FROM villes")
    fun getAllVilles(): Flow<List<Ville>>

    @Query("SELECT * FROM villes WHERE id = :id")
    suspend fun getVilleById(id: Int): Ville?
}