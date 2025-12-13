package com.example.maisonhotesapp.data.repository

import com.example.maisonhotesapp.data.dao.*
import com.example.maisonhotesapp.data.entity.*
import kotlinx.coroutines.flow.Flow

class MaisonRepository(
    private val regionDao: RegionDao,
    private val villeDao: VilleDao,
    private val maisonHoteDao: MaisonHoteDao,
    private val avisDao: AvisDao,
    private val imageDao: ImageDao
) {

    // ============================================
    // REGION OPERATIONS
    // ============================================

    fun getAllRegions(): Flow<List<Region>> = regionDao.getAllRegions()

    suspend fun getRegionById(id: Int): Region? = regionDao.getRegionById(id)

    suspend fun insertRegion(region: Region) = regionDao.insert(region)

    suspend fun updateRegion(region: Region) = regionDao.update(region)

    suspend fun deleteRegion(region: Region) = regionDao.delete(region)


    // ============================================
    // VILLE OPERATIONS
    // ============================================

    fun getVillesByRegion(regionId: Int): Flow<List<Ville>> = villeDao.getVillesByRegion(regionId)

    fun getAllVilles(): Flow<List<Ville>> = villeDao.getAllVilles()

    suspend fun getVilleById(id: Int): Ville? = villeDao.getVilleById(id)

    suspend fun insertVille(ville: Ville) = villeDao.insert(ville)

    suspend fun updateVille(ville: Ville) = villeDao.update(ville)

    suspend fun deleteVille(ville: Ville) = villeDao.delete(ville)


    // ============================================
    // MAISON HOTE OPERATIONS
    // ============================================

    fun getAllMaisonsHotes(): Flow<List<MaisonHote>> = maisonHoteDao.getAllMaisonsHotes()

    suspend fun getMaisonHoteById(id: Int): MaisonHote? = maisonHoteDao.getMaisonHoteById(id)

    suspend fun insertMaisonHote(maisonHote: MaisonHote): Long = maisonHoteDao.insert(maisonHote)

    suspend fun updateMaisonHote(maisonHote: MaisonHote) = maisonHoteDao.update(maisonHote)

    suspend fun deleteMaisonHote(maisonHote: MaisonHote) = maisonHoteDao.delete(maisonHote)

    fun getMaisonsByVille(villeId: Int): Flow<List<MaisonHote>> = maisonHoteDao.getMaisonsByVille(villeId)

    fun getMaisonsByPrix(prixMin: Double, prixMax: Double): Flow<List<MaisonHote>> =
        maisonHoteDao.getMaisonsByPrix(prixMin, prixMax)

    fun getMaisonsByNotation(notation: Float): Flow<List<MaisonHote>> =
        maisonHoteDao.getMaisonsByNotation(notation)

    fun rechercherMaisons(recherche: String): Flow<List<MaisonHote>> =
        maisonHoteDao.rechercherMaisons(recherche)

    fun getFavorisMaisons(): Flow<List<MaisonHote>> = maisonHoteDao.getFavorisMaisons()

    suspend fun updateFavorite(id: Int, favorite: Boolean) =
        maisonHoteDao.updateFavorite(id, favorite)


    // ============================================
    // AVIS OPERATIONS
    // ============================================

    fun getAvisByMaison(maisonHoteId: Int): Flow<List<Avis>> = avisDao.getAvisByMaison(maisonHoteId)

    suspend fun getAvisById(id: Int): Avis? = avisDao.getAvisById(id)

    suspend fun insertAvis(avis: Avis) = avisDao.insert(avis)

    suspend fun updateAvis(avis: Avis) = avisDao.update(avis)

    suspend fun deleteAvis(avis: Avis) = avisDao.delete(avis)

    suspend fun getMoyenneNotation(maisonHoteId: Int): Float? = avisDao.getMoyenneNotation(maisonHoteId)

    suspend fun getNombreAvis(maisonHoteId: Int): Int = avisDao.getNombreAvis(maisonHoteId)


    // ============================================
    // IMAGE OPERATIONS
    // ============================================

    fun getImagesByMaison(maisonHoteId: Int): Flow<List<Image>> = imageDao.getImagesByMaison(maisonHoteId)

    suspend fun getImageById(id: Int): Image? = imageDao.getImageById(id)

    suspend fun insertImage(image: Image) = imageDao.insert(image)

    suspend fun updateImage(image: Image) = imageDao.update(image)

    suspend fun deleteImage(image: Image) = imageDao.delete(image)
}