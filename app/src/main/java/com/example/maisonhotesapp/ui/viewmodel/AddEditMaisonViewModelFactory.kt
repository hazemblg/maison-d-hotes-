package com.example.maisonhotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.maisonhotes.data.repository.MaisonRepository

class AddEditMaisonViewModelFactory(private val repository: MaisonRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddEditMaisonViewModel(repository) as T
    }
}