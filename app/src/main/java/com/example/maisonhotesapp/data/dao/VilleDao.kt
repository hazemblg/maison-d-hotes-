package com.example.maisonhotesapp.data.dao

import androidx.room.*
import com.example.maisonhotesapp.data.entity.Ville
import kotlinx.coroutines.flow.Flow

@Dao
interface VilleDao {
    @Query("SELECT * FROM villes WHERE regionId = :regionId ORDER BY nom ASC")
    fun getVillesByRegion(regionId: Int): Flow<List<Ville>>

    @Query("SELECT * FROM villes ORDER BY nom ASC")
    fun getAllVilles(): Flow<List<Ville>>

    @Query("SELECT * FROM villes WHERE id = :id")
    suspend fun getVilleById(id: Int): Ville?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ville: Ville)

    @Update
    suspend fun update(ville: Ville)

    @Delete
    suspend fun delete(ville: Ville)
}