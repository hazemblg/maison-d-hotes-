package com.example.maisonhotesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.maisonhotesapp.data.entity.MaisonHote
import com.example.maisonhotesapp.data.repository.MaisonRepository
import kotlinx.coroutines.launch

class MaisonListViewModel(private val repository: MaisonRepository) : ViewModel() {

    // Récupérer toutes les maisons
    val allMaisonsHotes = repository.getAllMaisonsHotes().asLiveData()

    // Récupérer les favoris
    val favorisMaisonsHotes = repository.getFavorisMaisons().asLiveData()

    // Rechercher par mot-clé
    fun rechercher(query: String) = repository.rechercherMaisons(query).asLiveData()

    // Filtrer par prix
    fun getMaisonsByPrix(min: Double, max: Double) =
        repository.getMaisonsByPrix(min, max).asLiveData()

    // Filtrer par notation
    fun getMaisonsByNotation(notation: Float) =
        repository.getMaisonsByNotation(notation).asLiveData()

    // Ajouter/Retirer des favoris
    fun toggleFavorite(maisonHote: MaisonHote) {
        viewModelScope.launch {
            repository.updateFavorite(maisonHote.id, !maisonHote.isFavorite)
        }
    }

    // Supprimer une maison
    fun deleteMaison(maisonHote: MaisonHote) {
        viewModelScope.launch {
            repository.deleteMaisonHote(maisonHote)
        }
    }
}