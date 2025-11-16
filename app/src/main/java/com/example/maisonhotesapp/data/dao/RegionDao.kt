package com.example.maisonhotes.data.dao

import androidx.room.*
import com.example.maisonhotes.data.entity.Region
import kotlinx.coroutines.flow.Flow

@Dao
interface RegionDao {

    @Insert
    suspend fun insert(region: Region)

    @Update
    suspend fun update(region: Region)

    @Delete
    suspend fun delete(region: Region)

    @Query("SELECT * FROM regions")
    fun getAllRegions(): Flow<List<Region>>

    @Query("SELECT * FROM regions WHERE id = :id")
    suspend fun getRegionById(id: Int): Region?
}