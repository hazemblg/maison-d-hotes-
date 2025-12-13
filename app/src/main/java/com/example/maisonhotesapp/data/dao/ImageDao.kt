package com.example.maisonhotesapp.data.dao

import androidx.room.*
import com.example.maisonhotesapp.data.entity.Image
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Query("SELECT * FROM images WHERE maisonHoteId = :maisonHoteId ORDER BY ordreAffichage ASC")
    fun getImagesByMaison(maisonHoteId: Int): Flow<List<Image>>

    @Query("SELECT * FROM images WHERE id = :id")
    suspend fun getImageById(id: Int): Image?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: Image)

    @Update
    suspend fun update(image: Image)

    @Delete
    suspend fun delete(image: Image)
}