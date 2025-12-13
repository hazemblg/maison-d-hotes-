package com.example.maisonhotesapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maisonhotesapp.data.database.MaisonsHotesDatabase
import com.example.maisonhotesapp.data.repository.MaisonRepository
import com.example.maisonhotesapp.databinding.FragmentMaisonListBinding
import com.example.maisonhotesapp.ui.adapter.MaisonAdapter
import com.example.maisonhotesapp.ui.viewmodel.MaisonListViewModel
import com.example.maisonhotesapp.ui.viewmodel.MaisonListViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MaisonListFragment : Fragment() {

    private lateinit var binding: FragmentMaisonListBinding
    private lateinit var adapter: MaisonAdapter
    private val viewModel: MaisonListViewModel by viewModels {
        val database = MaisonsHotesDatabase.getInstance(requireContext())
        val repository = MaisonRepository(
            database.regionDao(),
            database.villeDao(),
            database.maisonHoteDao(),
            database.avisDao(),
            database.imageDao()
        )
        MaisonListViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMaisonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = MaisonAdapter(
            onItemClick = { maison ->
                val action = MaisonListFragmentDirections.actionMaisonListFragmentToMaisonDetailFragment(maison.id)
                findNavController().navigate(action)
            },
            onFavoriteClick = { maison ->
                viewModel.toggleFavorite(maison)
            }
        )
        binding.recyclerViewMaisons.adapter = adapter
        binding.recyclerViewMaisons.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupObservers() {
        viewModel.allMaisonsHotes.observe(viewLifecycleOwner) { maisons ->
            android.util.Log.d("MaisonListFragment", "Maisons loaded: ${maisons.size} items")

            // Masquer le ProgressBar
            binding.progressBar.visibility = View.GONE

            if (maisons.isEmpty()) {
                android.util.Log.w("MaisonListFragment", "⚠️ No maisons found in database!")
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.recyclerViewMaisons.visibility = View.GONE
            } else {
                android.util.Log.d("MaisonListFragment", "✅ Maisons: ${maisons.joinToString { it.nom }}")
                binding.tvEmptyState.visibility = View.GONE
                binding.recyclerViewMaisons.visibility = View.VISIBLE
            }
            adapter.submitList(maisons)
        }
    }

    private fun setupListeners() {
        // Bouton filtre
        binding.btnFilter.setOnClickListener {
            findNavController().navigate(com.example.maisonhotesapp.R.id.action_maisonListFragment_to_filtreFragment)
        }

        // FAB Ajouter une maison
        binding.fabAddMaison.setOnClickListener {
            findNavController().navigate(com.example.maisonhotesapp.R.id.action_maisonListFragment_to_addEditMaisonFragment)
        }

        // Recevoir les filtres appliqués
        findNavController().currentBackStackEntry?.savedStateHandle?.let { handle ->
            handle.getLiveData<Int?>("filtre_region_id").observe(viewLifecycleOwner) { regionId ->
                applyFilters()
            }
            handle.getLiveData<Int?>("filtre_ville_id").observe(viewLifecycleOwner) { villeId ->
                applyFilters()
            }
            handle.getLiveData<Double>("filtre_prix_min").observe(viewLifecycleOwner) { prixMin ->
                applyFilters()
            }
            handle.getLiveData<Double>("filtre_prix_max").observe(viewLifecycleOwner) { prixMax ->
                applyFilters()
            }
            handle.getLiveData<Float>("filtre_notation").observe(viewLifecycleOwner) { notation ->
                applyFilters()
            }
        }

        // SearchView listener
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    performSearch(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    applyFilters()
                } else {
                    performSearch(newText)
                }
                return true
            }
        })
    }

    private fun applyFilters() {
        val handle = findNavController().currentBackStackEntry?.savedStateHandle
        val regionId = handle?.get<Int?>("filtre_region_id")
        val villeId = handle?.get<Int?>("filtre_ville_id")
        val prixMin = handle?.get<Double>("filtre_prix_min") ?: 0.0
        val prixMax = handle?.get<Double>("filtre_prix_max") ?: Double.MAX_VALUE
        val notation = handle?.get<Float>("filtre_notation") ?: 0f

        viewModel.allMaisonsHotes.observe(viewLifecycleOwner) { maisons ->
            var filtered = maisons

            // Filtre par ville (qui inclut la région)
            if (villeId != null) {
                filtered = filtered.filter { it.villeId == villeId }
            } else if (regionId != null) {
                // Filtre par région si pas de ville spécifique
                // On devra chercher les villes de cette région
                lifecycleScope.launch {
                    val database = MaisonsHotesDatabase.getInstance(requireContext())
                    val villesInRegion = database.villeDao().getVillesByRegion(regionId).first()
                    val villeIds = villesInRegion.map { it.id }
                    val filteredByRegion = maisons.filter { it.villeId in villeIds }

                    val finalFiltered = filteredByRegion
                        .filter { it.prix in prixMin..prixMax }
                        .filter { it.notation >= notation }

                    adapter.submitList(finalFiltered)
                }
                return@observe
            }

            // Appliquer les autres filtres
            filtered = filtered
                .filter { it.prix in prixMin..prixMax }
                .filter { it.notation >= notation }

            adapter.submitList(filtered)
        }
    }

    private fun performSearch(query: String) {
        val searchResults = viewModel.rechercher(query)
        searchResults.observe(viewLifecycleOwner) { results ->
            adapter.submitList(results)
        }
    }
}