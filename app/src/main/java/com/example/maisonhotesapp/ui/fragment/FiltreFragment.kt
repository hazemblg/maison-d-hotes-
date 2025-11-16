package com.example.maisonhotes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.maisonhotes.data.database.MaisonsHotesDatabase
import com.example.maisonhotes.data.repository.MaisonRepository
import com.example.maisonhotes.databinding.FragmentFiltreBinding
import com.example.maisonhotes.ui.viewmodel.FiltreViewModel
import com.example.maisonhotes.ui.viewmodel.FiltreViewModelFactory

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

        setupListeners()
    }

    private fun setupListeners() {
        binding.apply {
            notationSlider.addOnChangeListener { _, value, _ ->
                notationValue.text = String.format("%.1f", value)
            }

            resetBtn.setOnClickListener {
                regionSpinner.setText("")
                villeSpinner.setText("")
                prixMin.text?.clear()
                prixMax.text?.clear()
                notationSlider.value = 0f
            }

            appliquerBtn.setOnClickListener {
                val prixMin = prixMin.text.toString().toDoubleOrNull() ?: 0.0
                val prixMax = prixMax.text.toString().toDoubleOrNull() ?: Double.MAX_VALUE
                val notation = notationSlider.value

                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "filtre_prix_min",
                    prixMin
                )
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "filtre_prix_max",
                    prixMax
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