package com.example.maisonhotesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.maisonhotesapp.data.repository.MaisonRepository

class FiltreViewModel(private val repository: MaisonRepository) : ViewModel() {

    // Récupérer toutes les régions
    val allRegions = repository.getAllRegions().asLiveData()

    // Récupérer toutes les villes
    val allVilles = repository.getAllVilles().asLiveData()

    // Récupérer villes par région
    fun getVillesByRegion(regionId: Int) =
        repository.getVillesByRegion(regionId).asLiveData()

    // Filtrer par prix
    fun getMaisonsByPrix(min: Double, max: Double) =
        repository.getMaisonsByPrix(min, max).asLiveData()

    // Filtrer par notation
    fun getMaisonsByNotation(notation: Float) =
        repository.getMaisonsByNotation(notation).asLiveData()

    // Filtrer par ville
    fun getMaisonsByVille(villeId: Int) =
        repository.getMaisonsByVille(villeId).asLiveData()
}