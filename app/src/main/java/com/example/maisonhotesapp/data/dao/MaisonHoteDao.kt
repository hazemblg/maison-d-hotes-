package com.example.maisonhotesapp.data.dao

import androidx.room.*
import com.example.maisonhotesapp.data.entity.MaisonHote
import kotlinx.coroutines.flow.Flow

@Dao
interface MaisonHoteDao {
    @Query("SELECT * FROM maisons_hotes ORDER BY nom ASC")
    fun getAllMaisonsHotes(): Flow<List<MaisonHote>>

    @Query("SELECT * FROM maisons_hotes WHERE id = :id")
    suspend fun getMaisonHoteById(id: Int): MaisonHote?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(maisonHote: MaisonHote): Long

    @Update
    suspend fun update(maisonHote: MaisonHote)

    @Delete
    suspend fun delete(maisonHote: MaisonHote)

    @Query("SELECT * FROM maisons_hotes WHERE villeId = :villeId ORDER BY nom ASC")
    fun getMaisonsByVille(villeId: Int): Flow<List<MaisonHote>>

    @Query("SELECT * FROM maisons_hotes WHERE prix BETWEEN :prixMin AND :prixMax ORDER BY prix ASC")
    fun getMaisonsByPrix(prixMin: Double, prixMax: Double): Flow<List<MaisonHote>>

    @Query("SELECT * FROM maisons_hotes WHERE notation >= :notation ORDER BY notation DESC")
    fun getMaisonsByNotation(notation: Float): Flow<List<MaisonHote>>

    @Query("SELECT * FROM maisons_hotes WHERE nom LIKE '%' || :recherche || '%' OR description LIKE '%' || :recherche || '%' ORDER BY nom ASC")
    fun rechercherMaisons(recherche: String): Flow<List<MaisonHote>>

    @Query("SELECT * FROM maisons_hotes WHERE isFavorite = 1 ORDER BY nom ASC")
    fun getFavorisMaisons(): Flow<List<MaisonHote>>

    @Query("UPDATE maisons_hotes SET isFavorite = :favorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, favorite: Boolean)
}