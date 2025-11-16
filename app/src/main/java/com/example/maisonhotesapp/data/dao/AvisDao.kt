package com.example.maisonhotes.data.dao

import androidx.room.*
import com.example.maisonhotes.data.entity.Avis
import kotlinx.coroutines.flow.Flow

@Dao
interface AvisDao {

    @Insert
    suspend fun insert(avis: Avis)

    @Update
    suspend fun update(avis: Avis)

    @Delete
    suspend fun delete(avis: Avis)

    @Query("SELECT * FROM avis WHERE maisonHoteId = :maisonHoteId ORDER BY dateAvis DESC")
    fun getAvisByMaison(maisonHoteId: Int): Flow<List<Avis>>

    @Query("SELECT AVG(notation) FROM avis WHERE maisonHoteId = :maisonHoteId")
    suspend fun getMoyenneNotation(maisonHoteId: Int): Float?

    @Query("SELECT COUNT(*) FROM avis WHERE maisonHoteId = :maisonHoteId")
    suspend fun getNombreAvis(maisonHoteId: Int): Int
}