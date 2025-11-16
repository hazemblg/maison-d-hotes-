package com.example.maisonhotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maisonhotes.data.entity.Image
import com.example.maisonhotes.data.entity.MaisonHote
import com.example.maisonhotes.data.repository.MaisonRepository
import kotlinx.coroutines.launch

class AddEditMaisonViewModel(private val repository: MaisonRepository) : ViewModel() {

    // Sauvegarder (insérer ou mettre à jour)
    fun saveMaison(maisonHote: MaisonHote) {
        viewModelScope.launch {
            if (maisonHote.id == 0) {
                // Nouvelle maison
                repository.insertMaisonHote(maisonHote)
            } else {
                // Mise à jour
                repository.updateMaisonHote(maisonHote)
            }
        }
    }

    // Supprimer une maison
    fun deleteMaison(maisonHote: MaisonHote) {
        viewModelScope.launch {
            repository.deleteMaisonHote(maisonHote)
        }
    }

    // Ajouter une image
    fun addImage(image: Image) {
        viewModelScope.launch {
            repository.insertImage(image)
        }
    }

    // Supprimer une image
    fun deleteImage(image: Image) {
        viewModelScope.launch {
            repository.deleteImage(image)
        }
    }
}