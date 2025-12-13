package com.example.maisonhotesapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maisonhotesapp.data.database.MaisonsHotesDatabase
import com.example.maisonhotesapp.data.repository.MaisonRepository
import com.example.maisonhotesapp.databinding.FragmentMaisonListBinding
import com.example.maisonhotesapp.ui.adapter.MaisonAdapter
import com.example.maisonhotesapp.ui.viewmodel.MaisonListViewModel
import com.example.maisonhotesapp.ui.viewmodel.MaisonListViewModelFactory

class FavoriFragment : Fragment() {

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
    }

    private fun setupRecyclerView() {
        adapter = MaisonAdapter(
            onItemClick = { maison ->
                val bundle = Bundle().apply {
                    putInt("maisonId", maison.id)
                }
                findNavController().navigate(com.example.maisonhotesapp.R.id.action_favoriFragment_to_maisonDetailFragment, bundle)
            },
            onFavoriteClick = { maison ->
                viewModel.toggleFavorite(maison)
            }
        )
        binding.recyclerViewMaisons.adapter = adapter
        binding.recyclerViewMaisons.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupObservers() {
        viewModel.favorisMaisonsHotes.observe(viewLifecycleOwner) { favoris ->
            adapter.submitList(favoris)
        }
    }
}

