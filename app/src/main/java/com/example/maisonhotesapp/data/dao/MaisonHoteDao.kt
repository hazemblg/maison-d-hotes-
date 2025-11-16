package com.example.maisonhotes.data.dao

import androidx.room.*
import com.example.maisonhotes.data.entity.MaisonHote
import kotlinx.coroutines.flow.Flow

@Dao
interface MaisonHoteDao {

    @Insert
    suspend fun insert(maisonHote: MaisonHote): Long

    @Update
    suspend fun update(maisonHote: MaisonHote)

    @Delete
    suspend fun delete(maisonHote: MaisonHote)

    @Query("SELECT * FROM maisons_hotes")
    fun getAllMaisonsHotes(): Flow<List<MaisonHote>>

    @Query("SELECT * FROM maisons_hotes WHERE id = :id")
    suspend fun getMaisonHoteById(id: Int): MaisonHote?

    @Query("SELECT * FROM maisons_hotes WHERE villeId = :villeId")
    fun getMaisonsByVille(villeId: Int): Flow<List<MaisonHote>>

    @Query("SELECT * FROM maisons_hotes WHERE prixParNuit BETWEEN :prixMin AND :prixMax")
    fun getMaisonsByPrix(prixMin: Double, prixMax: Double): Flow<List<MaisonHote>>

    @Query("SELECT * FROM maisons_hotes WHERE notation >= :notation ORDER BY notation DESC")
    fun getMaisonsByNotation(notation: Float): Flow<List<MaisonHote>>

    @Query("SELECT * FROM maisons_hotes WHERE nom LIKE '%' || :recherche || '%' OR description LIKE '%' || :recherche || '%'")
    fun rechercherMaisons(recherche: String): Flow<List<MaisonHote>>

    @Query("SELECT * FROM maisons_hotes WHERE estFavorite = 1")
    fun getFavorisMaisons(): Flow<List<MaisonHote>>

    @Query("UPDATE maisons_hotes SET estFavorite = :favorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, favorite: Boolean)
}