package com.example.maisonhotes.data.dao

import androidx.room.*
import com.example.maisonhotes.data.entity.Image
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Insert
    suspend fun insert(image: Image)

    @Delete
    suspend fun delete(image: Image)

    @Query("SELECT * FROM images WHERE maisonHoteId = :maisonHoteId ORDER BY ordreAffichage ASC")
    fun getImagesByMaison(maisonHoteId: Int): Flow<List<Image>>

    @Query("DELETE FROM images WHERE maisonHoteId = :maisonHoteId")
    suspend fun deleteImagesByMaison(maisonHoteId: Int)
}