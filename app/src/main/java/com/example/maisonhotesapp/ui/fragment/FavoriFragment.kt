package com.example.maisonhotes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maisonhotes.data.database.MaisonsHotesDatabase
import com.example.maisonhotes.data.repository.MaisonRepository
import com.example.maisonhotes.databinding.FragmentMaisonListBinding
import com.example.maisonhotes.ui.adapter.MaisonAdapter
import com.example.maisonhotes.ui.viewmodel.MaisonListViewModel
import com.example.maisonhotes.ui.viewmodel.MaisonListViewModelFactory

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
                val action = FavoriFragmentDirections.actionFavoriFragmentToMaisonDetailFragment(maison.id)
                findNavController().navigate(action)
            },
            onFavoriteClick = { maison ->
                viewModel.toggleFavorite(maison)
            }
        )
        binding.maisonsRecyclerView.adapter = adapter
        binding.maisonsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupObservers() {
        viewModel.favorisMaisonsHotes.observe(viewLifecycleOwner) { favoris ->
            adapter.submitList(favoris)
        }
    }
}