package com.example.maisonhotes.data

import android.content.Context
import com.example.maisonhotes.data.database.MaisonsHotesDatabase
import com.example.maisonhotes.data.entity.Region
import com.example.maisonhotes.data.entity.Ville
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DataInitializer {

    suspend fun initializeDatabase(context: Context) = withContext(Dispatchers.IO) {
        val database = MaisonsHotesDatabase.getInstance(context)
        val regionDao = database.regionDao()
        val villeDao = database.villeDao()

        // Vérifier si les données existent déjà
        val existingRegions = regionDao.getAllRegions()
        var hasData = false
        existingRegions.collect { regions ->
            hasData = regions.isNotEmpty()
        }

        if (!hasData) {
            // Insérer les 8 régions tunisiennes
            val regions = listOf(
                Region(id = 1, nom = "Tunis", description = "Capitale, centre économique et culturel"),
                Region(id = 2, nom = "Cap Bon", description = "Péninsule fertile au nord-est"),
                Region(id = 3, nom = "Nord Est", description = "Raf Raf, Bizerte, El Haouaria"),
                Region(id = 4, nom = "Sahel", description = "Sousse, Sfax, Kairouan"),
                Region(id = 5, nom = "Sud Est", description = "Gabès, Tataouine, Médenine"),
                Region(id = 6, nom = "Sud Ouest", description = "Tozeur, Nefta, Douz"),
                Region(id = 7, nom = "Kerkennah", description = "Îles Kerkennah"),
                Region(id = 8, nom = "Djerba", description = "Île de Djerba, station balnéaire")
            )

            // Insérer chaque région
            regions.forEach { region ->
                regionDao.insert(region)
            }

            // Insérer les villes par région
            val villes = listOf(
                // Région Tunis
                Ville(id = 1, nom = "Tunis", regionId = 1, latitude = 36.8065, longitude = 10.1686),
                Ville(id = 2, nom = "La Marsa", regionId = 1, latitude = 36.8629, longitude = 10.3262),
                Ville(id = 3, nom = "Carthage", regionId = 1, latitude = 36.8522, longitude = 10.3267),

                // Région Cap Bon
                Ville(id = 4, nom = "Nabeul", regionId = 2, latitude = 36.4527, longitude = 10.7355),
                Ville(id = 5, nom = "Hammamet", regionId = 2, latitude = 36.3968, longitude = 10.6135),
                Ville(id = 6, nom = "Kelibia", regionId = 2, latitude = 36.7546, longitude = 11.1505),

                // Région Nord Est
                Ville(id = 7, nom = "Bizerte", regionId = 3, latitude = 37.2741, longitude = 9.8735),
                Ville(id = 8, nom = "Raf Raf", regionId = 3, latitude = 37.4233, longitude = 9.7373),
                Ville(id = 9, nom = "El Haouaria", regionId = 3, latitude = 36.9354, longitude = 10.9734),

                // Région Sahel
                Ville(id = 10, nom = "Sousse", regionId = 4, latitude = 35.8256, longitude = 10.6369),
                Ville(id = 11, nom = "Sfax", regionId = 4, latitude = 34.7405, longitude = 10.7603),
                Ville(id = 12, nom = "Kairouan", regionId = 4, latitude = 35.6711, longitude = 10.1056),

                // Région Sud Est
                Ville(id = 13, nom = "Gabès", regionId = 5, latitude = 33.8869, longitude = 10.0994),
                Ville(id = 14, nom = "Tataouine", regionId = 5, latitude = 33.9369, longitude = 10.4547),
                Ville(id = 15, nom = "Médenine", regionId = 5, latitude = 33.3547, longitude = 10.5063),

                // Région Sud Ouest
                Ville(id = 16, nom = "Tozeur", regionId = 6, latitude = 33.9197, longitude = 8.1339),
                Ville(id = 17, nom = "Nefta", regionId = 6, latitude = 33.8568, longitude = 7.8858),
                Ville(id = 18, nom = "Douz", regionId = 6, latitude = 33.4654, longitude = 9.1255),

                // Région Kerkennah
                Ville(id = 19, nom = "Kerkennah", regionId = 7, latitude = 34.7167, longitude = 11.2667),

                // Région Djerba
                Ville(id = 20, nom = "Djerba", regionId = 8, latitude = 33.8067, longitude = 10.9503),
                Ville(id = 21, nom = "Houmt Souk", regionId = 8, latitude = 33.8733, longitude = 10.8967),
                Ville(id = 22, nom = "Midoun", regionId = 8, latitude = 33.7467, longitude = 10.4133)
            )

            // Insérer chaque ville
            villes.forEach { ville ->
                villeDao.insert(ville)
            }
        }
    }
}