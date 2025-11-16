package com.example.maisonhotes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.maisonhotes.data.database.MaisonsHotesDatabase
import com.example.maisonhotes.data.entity.MaisonHote
import com.example.maisonhotes.data.repository.MaisonRepository
import com.example.maisonhotes.databinding.FragmentAddEditMaisonBinding
import com.example.maisonhotes.ui.viewmodel.AddEditMaisonViewModel
import com.example.maisonhotes.ui.viewmodel.AddEditMaisonViewModelFactory

class AddEditMaisonFragment : Fragment() {

    private lateinit var binding: FragmentAddEditMaisonBinding
    private val args: AddEditMaisonFragmentArgs by navArgs()
    private val viewModel: AddEditMaisonViewModel by viewModels {
        val database = MaisonsHotesDatabase.getInstance(requireContext())
        val repository = MaisonRepository(
            database.regionDao(),
            database.villeDao(),
            database.maisonHoteDao(),
            database.avisDao(),
            database.imageDao()
        )
        AddEditMaisonViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEditMaisonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        binding.apply {
            saveBtn.setOnClickListener {
                val nom = nomInput.text.toString()
                val description = descriptionInput.text.toString()
                val prix = prixInput.text.toString().toDoubleOrNull() ?: 0.0
                val telephone = telephoneInput.text.toString()
                val email = emailInput.text.toString()

                val maison = MaisonHote(
                    id = args.maisonId,
                    nom = nom,
                    description = description,
                    villeId = 1, // À améliorer avec un sélecteur
                    adresse = "",
                    latitude = 0.0,
                    longitude = 0.0,
                    prixParNuit = prix,
                    telephone = telephone,
                    email = email,
                    imageUrl = "",
                    capaciteMax = 1,
                    chambres = 1,
                    sallesDeBain = 1
                )

                viewModel.saveMaison(maison)
            }
        }
    }
}