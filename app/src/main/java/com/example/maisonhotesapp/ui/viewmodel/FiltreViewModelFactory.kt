package com.example.maisonhotesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.maisonhotesapp.data.repository.MaisonRepository

class FiltreViewModelFactory(private val repository: MaisonRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FiltreViewModel(repository) as T
    }
}