package com.example.maisonhotesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.maisonhotesapp.data.entity.Avis
import com.example.maisonhotesapp.data.entity.MaisonHote
import com.example.maisonhotesapp.data.repository.MaisonRepository
import kotlinx.coroutines.launch

class MaisonDetailViewModel(
    private val repository: MaisonRepository,
    private val maisonId: Int
) : ViewModel() {

    private val _maisonHote = androidx.lifecycle.MutableLiveData<MaisonHote?>()
    val maisonHote: androidx.lifecycle.LiveData<MaisonHote?> = _maisonHote

    // Récupérer les avis
    val avis = repository.getAvisByMaison(maisonId).asLiveData()

    // Récupérer les images
    val images = repository.getImagesByMaison(maisonId).asLiveData()

    init {
        viewModelScope.launch {
            val maison = repository.getMaisonHoteById(maisonId)
            _maisonHote.postValue(maison)
        }
    }

    fun getMaisonHote(): MaisonHote? = _maisonHote.value

    // Ajouter un avis
    fun ajouterAvis(avis: Avis) {
        viewModelScope.launch {
            repository.insertAvis(avis)

            // Mettre à jour la notation moyenne
            val moyenneNotation = repository.getMoyenneNotation(maisonId) ?: 0f
            val nombreAvis = repository.getNombreAvis(maisonId)

            _maisonHote.value?.let { currentMaison ->
                val maisonUpdated = currentMaison.copy(
                    notation = moyenneNotation,
                    nombreAvis = nombreAvis
                )
                repository.updateMaisonHote(maisonUpdated)
                _maisonHote.postValue(maisonUpdated)
            }
        }
    }

    // Ajouter/Retirer des favoris
    fun toggleFavorite() {
        viewModelScope.launch {
            _maisonHote.value?.let { currentMaison ->
                repository.updateFavorite(currentMaison.id, !currentMaison.isFavorite)
                val maisonUpdated = currentMaison.copy(isFavorite = !currentMaison.isFavorite)
                _maisonHote.postValue(maisonUpdated)
            }
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