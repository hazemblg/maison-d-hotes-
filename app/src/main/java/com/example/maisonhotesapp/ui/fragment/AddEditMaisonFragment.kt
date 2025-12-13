package com.example.maisonhotesapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.maisonhotesapp.data.database.MaisonsHotesDatabase
import com.example.maisonhotesapp.data.entity.MaisonHote
import com.example.maisonhotesapp.data.entity.Region
import com.example.maisonhotesapp.data.entity.Ville
import com.example.maisonhotesapp.data.repository.MaisonRepository
import com.example.maisonhotesapp.databinding.FragmentAddEditMaisonBinding
import com.example.maisonhotesapp.ui.viewmodel.AddEditMaisonViewModel
import com.example.maisonhotesapp.ui.viewmodel.AddEditMaisonViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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

    private var regions = listOf<Region>()
    private var villes = listOf<Ville>()
    private var selectedRegionId: Int? = null
    private var selectedVilleId: Int? = null

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

        setupSpinners()
        setupListeners()
    }

    private fun setupSpinners() {
        lifecycleScope.launch {
            val database = MaisonsHotesDatabase.getInstance(requireContext())

            // Charger les régions
            regions = database.regionDao().getAllRegions().first()
            val regionNames = regions.map { it.nom }
            val regionAdapter = ArrayAdapter(
                requireContext(),
                com.example.maisonhotesapp.R.layout.spinner_item,
                regionNames
            )
            binding.regionSpinner.setAdapter(regionAdapter)

            // Quand on sélectionne une région, charger ses villes
            binding.regionSpinner.setOnItemClickListener { _, _, position, _ ->
                selectedRegionId = regions[position].id
                loadVillesByRegion(selectedRegionId!!)
            }
        }
    }

    private fun loadVillesByRegion(regionId: Int) {
        lifecycleScope.launch {
            val database = MaisonsHotesDatabase.getInstance(requireContext())
            villes = database.villeDao().getVillesByRegion(regionId).first()

            val villeNames = villes.map { it.nom }
            val villeAdapter = ArrayAdapter(
                requireContext(),
                com.example.maisonhotesapp.R.layout.spinner_item,
                villeNames
            )
            binding.villeSpinner.setAdapter(villeAdapter)
            binding.villeSpinner.setText("", false)
            selectedVilleId = null
        }
    }

    private fun setupListeners() {
        // Gestion de la sélection de ville
        binding.villeSpinner.setOnItemClickListener { _, _, position, _ ->
            selectedVilleId = villes[position].id
        }
        binding.apply {
            saveBtn.setOnClickListener {
                // Validation
                if (selectedVilleId == null) {
                    Toast.makeText(requireContext(), "Veuillez sélectionner une ville", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val nom = nomInput.text.toString()
                if (nom.isEmpty()) {
                    Toast.makeText(requireContext(), "Veuillez entrer un nom", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val description = descriptionInput.text.toString()
                val prix = prixInput.text.toString().toDoubleOrNull() ?: 0.0
                val telephone = telephoneInput.text.toString()
                val email = emailInput.text.toString()
                val siteWeb = siteWebInput.text.toString()
                val facebook = facebookInput.text.toString()
                val instagram = instagramInput.text.toString()
                val imageUrl = imageUrlInput.text.toString()
                val amenities = amenitiesInput.text.toString()
                val nombreChambres = chambresInput.text.toString().toIntOrNull() ?: 1
                val nombreLits = litsInput.text.toString().toIntOrNull() ?: 1

                val maison = MaisonHote(
                    id = 0, // 0 pour une nouvelle maison (autoGenerate)
                    nom = nom,
                    description = description,
                    villeId = selectedVilleId!!,
                    latitude = 0.0, // Peut être amélioré avec géolocalisation
                    longitude = 0.0,
                    prix = prix,
                    notation = 0f,
                    nombreAvis = 0,
                    telephone = telephone,
                    email = email,
                    siteWeb = siteWeb,
                    facebook = facebook,
                    instagram = instagram,
                    nombreChambres = nombreChambres,
                    nombreLits = nombreLits,
                    amenities = amenities,
                    isFavorite = false,
                    imageUrl = imageUrl
                )

                viewModel.saveMaison(maison)

                Toast.makeText(requireContext(), "Maison ajoutée avec succès!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }

            cancelBtn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}