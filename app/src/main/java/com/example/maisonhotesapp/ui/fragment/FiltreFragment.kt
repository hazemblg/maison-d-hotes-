package com.example.maisonhotesapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.maisonhotesapp.data.database.MaisonsHotesDatabase
import com.example.maisonhotesapp.data.entity.Region
import com.example.maisonhotesapp.data.entity.Ville
import com.example.maisonhotesapp.data.repository.MaisonRepository
import com.example.maisonhotesapp.databinding.FragmentFiltreBinding
import com.example.maisonhotesapp.ui.viewmodel.FiltreViewModel
import com.example.maisonhotesapp.ui.viewmodel.FiltreViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FiltreFragment : Fragment() {

    private lateinit var binding: FragmentFiltreBinding
    private val viewModel: FiltreViewModel by viewModels {
        val database = MaisonsHotesDatabase.getInstance(requireContext())
        val repository = MaisonRepository(
            database.regionDao(),
            database.villeDao(),
            database.maisonHoteDao(),
            database.avisDao(),
            database.imageDao()
        )
        FiltreViewModelFactory(repository)
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
        binding = FragmentFiltreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinners()
        setupListeners()
    }

    private fun setupSpinners() {
        lifecycleScope.launch {
            // Charger les régions
            val database = MaisonsHotesDatabase.getInstance(requireContext())
            regions = database.regionDao().getAllRegions().first()

            val regionNames = listOf("Toutes les régions") + regions.map { it.nom }
            val regionAdapter = ArrayAdapter(
                requireContext(),
                com.example.maisonhotesapp.R.layout.spinner_item,
                regionNames
            )
            binding.regionSpinner.setAdapter(regionAdapter)

            // Quand on sélectionne une région, charger ses villes
            binding.regionSpinner.setOnItemClickListener { _, _, position, _ ->
                if (position == 0) {
                    // "Toutes les régions" sélectionné
                    selectedRegionId = null
                    loadAllVilles()
                } else {
                    selectedRegionId = regions[position - 1].id
                    loadVillesByRegion(selectedRegionId!!)
                }
            }

            // Charger toutes les villes initialement
            loadAllVilles()
        }
    }

    private fun loadAllVilles() {
        lifecycleScope.launch {
            val database = MaisonsHotesDatabase.getInstance(requireContext())
            villes = database.villeDao().getAllVilles().first()

            val villeNames = listOf("Toutes les villes") + villes.map { it.nom }
            val villeAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                villeNames
            )
            binding.villeSpinner.setAdapter(villeAdapter)
            binding.villeSpinner.setText("", false)
            selectedVilleId = null
        }
    }

    private fun loadVillesByRegion(regionId: Int) {
        lifecycleScope.launch {
            val database = MaisonsHotesDatabase.getInstance(requireContext())
            villes = database.villeDao().getVillesByRegion(regionId).first()

            val villeNames = listOf("Toutes les villes") + villes.map { it.nom }
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
        binding.apply {
            // Gestion de la sélection de ville
            villeSpinner.setOnItemClickListener { _, _, position, _ ->
                selectedVilleId = if (position == 0) null else villes[position - 1].id
            }

            notationSlider.addOnChangeListener { _, value, _ ->
                notationValue.text = String.format(java.util.Locale.getDefault(), "%.1f", value)
            }

            resetBtn.setOnClickListener {
                regionSpinner.setText("", false)
                villeSpinner.setText("", false)
                prixMin.text?.clear()
                prixMax.text?.clear()
                notationSlider.value = 0f
                selectedRegionId = null
                selectedVilleId = null
                loadAllVilles()
            }

            appliquerBtn.setOnClickListener {
                val minPrix = prixMin.text.toString().toDoubleOrNull() ?: 0.0
                val maxPrix = prixMax.text.toString().toDoubleOrNull() ?: Double.MAX_VALUE
                val notation = notationSlider.value

                // Envoyer les filtres à la page précédente
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "filtre_region_id",
                    selectedRegionId
                )
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "filtre_ville_id",
                    selectedVilleId
                )
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "filtre_prix_min",
                    minPrix
                )
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "filtre_prix_max",
                    maxPrix
                )
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "filtre_notation",
                    notation
                )

                findNavController().popBackStack()
            }
        }
    }
}
