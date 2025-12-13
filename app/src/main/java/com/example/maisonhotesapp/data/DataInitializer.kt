package com.example.maisonhotesapp.data

import android.content.Context
import com.example.maisonhotesapp.data.database.MaisonsHotesDatabase
import com.example.maisonhotesapp.data.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

object DataInitializer {

    suspend fun initializeDatabase(context: Context) = withContext(Dispatchers.IO) {
        val database = MaisonsHotesDatabase.getInstance(context)
        val regionDao = database.regionDao()
        val villeDao = database.villeDao()
        val maisonHoteDao = database.maisonHoteDao()
        val imageDao = database.imageDao()
        val avisDao = database.avisDao()

        // Vérifier si les données existent déjà
        val existingRegions = regionDao.getAllRegions().first()

        if (existingRegions.isEmpty()) {
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
                Ville(id = 8, nom = "Raf Raf", regionId = 3, latitude = 37.3500, longitude = 9.7833),
                Ville(id = 9, nom = "El Haouaria", regionId = 3, latitude = 36.9500, longitude = 11.0167),

                // Région Sahel
                Ville(id = 10, nom = "Sousse", regionId = 4, latitude = 35.8256, longitude = 10.6369),
                Ville(id = 11, nom = "Sfax", regionId = 4, latitude = 34.7405, longitude = 10.7603),
                Ville(id = 12, nom = "Kairouan", regionId = 4, latitude = 35.6781, longitude = 10.1051),

                // Région Sud Est
                Ville(id = 13, nom = "Gabès", regionId = 5, latitude = 33.8869, longitude = 10.0994),
                Ville(id = 14, nom = "Tataouine", regionId = 5, latitude = 32.9289, longitude = 10.4502),
                Ville(id = 15, nom = "Médenine", regionId = 5, latitude = 33.3605, longitude = 10.5048),

                // Région Sud Ouest
                Ville(id = 16, nom = "Tozeur", regionId = 6, latitude = 33.9197, longitude = 8.1347),
                Ville(id = 17, nom = "Nefta", regionId = 6, latitude = 33.8622, longitude = 7.8869),
                Ville(id = 18, nom = "Douz", regionId = 6, latitude = 33.4678, longitude = 9.1956),

                // Région Kerkennah
                Ville(id = 19, nom = "Kerkennah", regionId = 7, latitude = 34.6333, longitude = 11.2833),

                // Région Djerba
                Ville(id = 20, nom = "Djerba", regionId = 8, latitude = 33.8081, longitude = 10.9408),
                Ville(id = 21, nom = "Midoun", regionId = 8, latitude = 33.7500, longitude = 10.8333)
            )

            villes.forEach { ville ->
                villeDao.insert(ville)
            }

            // Insérer des exemples de maisons d'hôtes
            val maisonsHotes = listOf(
                MaisonHote(
                    id = 1,
                    nom = "Maison Traditionnelle Tunis",
                    description = "Magnifique maison traditionnelle tunisienne avec riad au cœur de la médina",
                    villeId = 1,
                    latitude = 36.8065,
                    longitude = 10.1686,
                    prix = 80.0,
                    notation = 4.5f,
                    nombreAvis = 12,
                    telephone = "+216 71 123 456",
                    email = "contact@maisontunisienne.tn",
                    siteWeb = "www.maisontunisienne.tn",
                    facebook = "facebook.com/maisontunisienne",
                    instagram = "instagram.com/maisontunisienne",
                    nombreChambres = 4,
                    nombreLits = 8,
                    amenities = "WiFi,Climatisation,Petit déjeuner,Parking,Terrasse",
                    isFavorite = false,
                    imageUrl = "maison_tunis_1"
                ),
                MaisonHote(
                    id = 2,
                    nom = "Villa Hammamet Plage",
                    description = "Vue directe sur la plage, ambiance balnéaire avec piscine",
                    villeId = 5,
                    latitude = 36.3968,
                    longitude = 10.6135,
                    prix = 120.0,
                    notation = 4.8f,
                    nombreAvis = 25,
                    telephone = "+216 72 234 567",
                    email = "info@villahammamet.tn",
                    siteWeb = "www.villahammamet.tn",
                    facebook = "facebook.com/villahammamet",
                    instagram = "instagram.com/villahammamet",
                    nombreChambres = 6,
                    nombreLits = 12,
                    amenities = "WiFi,Climatisation,Petit déjeuner,Parking,Piscine,Plage,Restaurant",
                    isFavorite = false,
                    imageUrl = "villa_hammamet_1"
                ),
                MaisonHote(
                    id = 3,
                    nom = "Gîte Rural Djerba",
                    description = "Authentique gîte rural avec vue sur le désert et les palmiers",
                    villeId = 20,
                    latitude = 33.8081,
                    longitude = 10.9408,
                    prix = 60.0,
                    notation = 4.3f,
                    nombreAvis = 8,
                    telephone = "+216 75 345 678",
                    email = "gite@djerba.tn",
                    siteWeb = "www.giteruraldjerba.tn",
                    facebook = "facebook.com/giteruraldjerba",
                    instagram = "instagram.com/giteruraldjerba",
                    nombreChambres = 3,
                    nombreLits = 6,
                    amenities = "WiFi,Climatisation,Petit déjeuner,Parking",
                    isFavorite = false,
                    imageUrl = "gite_djerba_1"
                ),
                MaisonHote(
                    id = 4,
                    nom = "Dar Saïd Sousse",
                    description = "Maison d'hôtes confortable au cœur de la médina de Sousse",
                    villeId = 10,
                    latitude = 35.8256,
                    longitude = 10.6369,
                    prix = 75.0,
                    notation = 4.6f,
                    nombreAvis = 18,
                    telephone = "+216 73 456 789",
                    email = "dar@sousse.tn",
                    siteWeb = "www.darsousse.tn",
                    facebook = "facebook.com/darsousse",
                    instagram = "instagram.com/darsousse",
                    nombreChambres = 5,
                    nombreLits = 10,
                    amenities = "WiFi,Climatisation,Petit déjeuner,Parking,Terrasse",
                    isFavorite = false,
                    imageUrl = "dar_sousse_1"
                ),
                MaisonHote(
                    id = 5,
                    nom = "Riad Carthage",
                    description = "Magnifique riad traditionnel avec patio andalou, à proximité des ruines de Carthage",
                    villeId = 3,
                    latitude = 36.8522,
                    longitude = 10.3267,
                    prix = 95.0,
                    notation = 4.7f,
                    nombreAvis = 22,
                    telephone = "+216 71 987 654",
                    email = "contact@riadcarthage.tn",
                    siteWeb = "www.riadcarthage.tn",
                    facebook = "facebook.com/riadcarthage",
                    instagram = "instagram.com/riadcarthage",
                    nombreChambres = 6,
                    nombreLits = 12,
                    amenities = "WiFi,Climatisation,Petit déjeuner,Parking,Piscine,Spa,Terrasse",
                    isFavorite = false,
                    imageUrl = "riad_carthage_1"
                )
            )

            maisonsHotes.forEach { maison ->
                maisonHoteDao.insert(maison)
            }

            // Insérer des exemples d'images
            val images = listOf(
                Image(id = 1, maisonHoteId = 1, url = "maison_tunis_1", ordreAffichage = 1),
                Image(id = 2, maisonHoteId = 1, url = "maison_tunis_2", ordreAffichage = 2),
                Image(id = 3, maisonHoteId = 1, url = "maison_tunis_3", ordreAffichage = 3),
                Image(id = 4, maisonHoteId = 1, url = "maison_tunis_4", ordreAffichage = 4),
                Image(id = 5, maisonHoteId = 2, url = "villa_hammamet_1", ordreAffichage = 1),
                Image(id = 6, maisonHoteId = 2, url = "villa_hammamet_2", ordreAffichage = 2),
                Image(id = 7, maisonHoteId = 2, url = "villa_hammamet_3", ordreAffichage = 3),
                Image(id = 8, maisonHoteId = 2, url = "villa_hammamet_4", ordreAffichage = 4),
                Image(id = 9, maisonHoteId = 3, url = "gite_djerba_1", ordreAffichage = 1),
                Image(id = 10, maisonHoteId = 3, url = "gite_djerba_2", ordreAffichage = 2),
                Image(id = 11, maisonHoteId = 3, url = "gite_djerba_3", ordreAffichage = 3),
                Image(id = 12, maisonHoteId = 3, url = "gite_djerba_4", ordreAffichage = 4),
                Image(id = 13, maisonHoteId = 4, url = "dar_sousse_1", ordreAffichage = 1),
                Image(id = 14, maisonHoteId = 4, url = "dar_sousse_2", ordreAffichage = 2),
                Image(id = 15, maisonHoteId = 4, url = "dar_sousse_3", ordreAffichage = 3),
                Image(id = 16, maisonHoteId = 4, url = "dar_sousse_4", ordreAffichage = 4),
                Image(id = 17, maisonHoteId = 5, url = "riad_carthage_1", ordreAffichage = 1),
                Image(id = 18, maisonHoteId = 5, url = "riad_carthage_2", ordreAffichage = 2),
                Image(id = 19, maisonHoteId = 5, url = "riad_carthage_3", ordreAffichage = 3),
                Image(id = 20, maisonHoteId = 5, url = "riad_carthage_4", ordreAffichage = 4)
            )

            images.forEach { image ->
                imageDao.insert(image)
            }

            // Insérer des exemples d'avis
            val avis = listOf(
                Avis(id = 1, maisonHoteId = 1, auteur = "Ahmed", note = 5f, contenu = "Excellent accueil, très propre et confortable", dateAvis = "2024-11-15"),
                Avis(id = 2, maisonHoteId = 1, auteur = "Fatima", note = 4f, contenu = "Bon rapport qualité-prix, petit déjeuner délicieux", dateAvis = "2024-11-10"),
                Avis(id = 3, maisonHoteId = 2, auteur = "Mohamed", note = 5f, contenu = "Magnifique vue, piscine impeccable, service excellent", dateAvis = "2024-11-20"),
                Avis(id = 4, maisonHoteId = 3, auteur = "Nadia", note = 4f, contenu = "Authentique et dépaysant, accueil chaleureux", dateAvis = "2024-11-12"),
                Avis(id = 5, maisonHoteId = 4, auteur = "Karim", note = 4.5f, contenu = "Très bon, cadre traditionnel magnifique", dateAvis = "2024-11-18"),
                Avis(id = 6, maisonHoteId = 5, auteur = "Sarah", note = 5f, contenu = "Riad exceptionnel, patio magnifique et spa relaxant", dateAvis = "2024-11-22"),
                Avis(id = 7, maisonHoteId = 5, auteur = "Youssef", note = 4.5f, contenu = "Très bel endroit près de Carthage, chambres spacieuses", dateAvis = "2024-11-19")
            )

            avis.forEach { a ->
                avisDao.insert(a)
            }
        }
    }
}

