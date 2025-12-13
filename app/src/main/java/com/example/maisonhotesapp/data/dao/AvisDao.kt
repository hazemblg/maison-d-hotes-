package com.example.maisonhotesapp.data.dao

import androidx.room.*
import com.example.maisonhotesapp.data.entity.Avis
import kotlinx.coroutines.flow.Flow

@Dao
interface AvisDao {
    @Query("SELECT * FROM avis WHERE maisonHoteId = :maisonHoteId ORDER BY dateAvis DESC")
    fun getAvisByMaison(maisonHoteId: Int): Flow<List<Avis>>

    @Query("SELECT * FROM avis WHERE id = :id")
    suspend fun getAvisById(id: Int): Avis?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(avis: Avis)

    @Update
    suspend fun update(avis: Avis)

    @Delete
    suspend fun delete(avis: Avis)

    @Query("SELECT AVG(note) FROM avis WHERE maisonHoteId = :maisonHoteId")
    suspend fun getMoyenneNotation(maisonHoteId: Int): Float?

    @Query("SELECT COUNT(*) FROM avis WHERE maisonHoteId = :maisonHoteId")
    suspend fun getNombreAvis(maisonHoteId: Int): Int
}