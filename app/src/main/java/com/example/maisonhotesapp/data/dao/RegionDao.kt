package com.example.maisonhotesapp.data.dao

import androidx.room.*
import com.example.maisonhotesapp.data.entity.Region
import kotlinx.coroutines.flow.Flow

@Dao
interface RegionDao {
    @Query("SELECT * FROM regions ORDER BY nom ASC")
    fun getAllRegions(): Flow<List<Region>>

    @Query("SELECT * FROM regions WHERE id = :id")
    suspend fun getRegionById(id: Int): Region?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(region: Region)

    @Update
    suspend fun update(region: Region)

    @Delete
    suspend fun delete(region: Region)
}