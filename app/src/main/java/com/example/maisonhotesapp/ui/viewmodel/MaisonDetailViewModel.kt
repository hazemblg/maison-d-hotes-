package com.example.maisonhotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.maisonhotes.data.entity.Avis
import com.example.maisonhotes.data.entity.MaisonHote
import com.example.maisonhotes.data.repository.MaisonRepository
import kotlinx.coroutines.launch

class MaisonDetailViewModel(
    private val repository: MaisonRepository,
    private val maisonId: Int
) : ViewModel() {

    private lateinit var maisonHote: MaisonHote

    // Récupérer les avis
    val avis = repository.getAvisByMaison(maisonId).asLiveData()

    // Récupérer les images
    val images = repository.getImagesByMaison(maisonId).asLiveData()

    init {
        viewModelScope.launch {
            maisonHote = repository.getMaisonHoteById(maisonId) ?: return@launch
        }
    }

    fun getMaisonHote() = maisonHote

    // Ajouter un avis
    fun ajouterAvis(avis: Avis) {
        viewModelScope.launch {
            repository.insertAvis(avis)

            // Mettre à jour la notation moyenne
            val moyenneNotation = repository.getMoyenneNotation(maisonId) ?: 0f
            val nombreAvis = repository.getNombreAvis(maisonId)

            val maisonUpdated = maisonHote.copy(
                notation = moyenneNotation,
                nombreAvis = nombreAvis
            )
            repository.updateMaisonHote(maisonUpdated)
            maisonHote = maisonUpdated
        }
    }

    // Ajouter/Retirer des favoris
    fun toggleFavorite() {
        viewModelScope.launch {
            repository.updateFavorite(maisonHote.id, !maisonHote.estFavorite)
            maisonHote = maisonHote.copy(estFavorite = !maisonHote.estFavorite)
        }
    }

    // Factory pour créer le ViewModel avec paramètres
    class Factory(
        private val repository: MaisonRepository,
        private val maisonId: Int
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MaisonDetailViewModel::class.java)) {
                return MaisonDetailViewModel(repository, maisonId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}