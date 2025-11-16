package com.example.maisonhotes.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.maisonhotes.data.database.MaisonsHotesDatabase
import com.example.maisonhotes.data.repository.MaisonRepository
import com.example.maisonhotes.databinding.FragmentMaisonDetailBinding
import com.example.maisonhotes.ui.adapter.AvisAdapter
import com.example.maisonhotes.ui.adapter.ImagePagerAdapter
import com.example.maisonhotes.ui.viewmodel.MaisonDetailViewModel

class MaisonDetailFragment : Fragment() {

    private lateinit var binding: FragmentMaisonDetailBinding
    private val args: MaisonDetailFragmentArgs by navArgs()
    private val viewModel: MaisonDetailViewModel by viewModels {
        val database = MaisonsHotesDatabase.getInstance(requireContext())
        val repository = MaisonRepository(
            database.regionDao(),
            database.villeDao(),
            database.maisonHoteDao(),
            database.avisDao(),
            database.imageDao()
        )
        MaisonDetailViewModel.Factory(repository, args.maisonId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMaisonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
        setupListeners()
    }

    private fun setupUI() {
        val maison = viewModel.getMaisonHote()

        binding.apply {
            nomMaison.text = maison.nom
            description.text = maison.description
            adresse.text = maison.adresse
            telephone.text = maison.telephone
            email.text = maison.email
            prixNuit.text = "${maison.prixParNuit}€ / nuit"
            capacite.text = maison.capaciteMax.toString()
            chambres.text = maison.chambres.toString()
            sallesBain.text = maison.sallesDeBain.toString()
            ratingBar.rating = maison.notation
            nombreAvis.text = "${maison.nombreAvis} avis"
        }
    }

    private fun setupObservers() {
        // Observer les images
        viewModel.images.observe(viewLifecycleOwner) { images ->
            val adapter = ImagePagerAdapter(images)
            binding.imagePager.adapter = adapter
        }

        // Observer les avis
        viewModel.avis.observe(viewLifecycleOwner) { avis ->
            val avisAdapter = AvisAdapter()
            avisAdapter.submitList(avis)
            binding.avisRecyclerView.adapter = avisAdapter
        }
    }

    private fun setupListeners() {
        binding.apply {
            // Bouton favori
            favoriteFab.setOnClickListener {
                viewModel.toggleFavorite()
            }

            // Bouton appeler
            callBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${telephone.text}"))
                startActivity(intent)
            }

            // Bouton email
            emailBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "message/rfc822"
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(email.text.toString()))
                }
                startActivity(Intent.createChooser(intent, "Envoyer un email"))
            }

            // Bouton itinéraire
            itinerairBtn.setOnClickListener {
                val maison = viewModel.getMaisonHote()
                val uri = Uri.parse("geo:${maison.latitude},${maison.longitude}")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }

            // Réseaux sociaux
            facebookBtn.setOnClickListener {
                val maison = viewModel.getMaisonHote()
                if (maison.facebook.isNotEmpty()) {
                    openUrl(maison.facebook)
                }
            }

            instagramBtn.setOnClickListener {
                val maison = viewModel.getMaisonHote()
                if (maison.instagram.isNotEmpty()) {
                    openUrl(maison.instagram)
                }
            }

            websiteBtn.setOnClickListener {
                val maison = viewModel.getMaisonHote()
                if (maison.siteWeb.isNotEmpty()) {
                    openUrl(maison.siteWeb)
                }
            }
        }
    }

    private fun openUrl(url: String) {
        val uri = Uri.parse(if (!url.startsWith("http")) "https://$url" else url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}