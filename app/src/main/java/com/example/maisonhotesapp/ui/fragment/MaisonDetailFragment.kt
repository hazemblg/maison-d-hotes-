package com.example.maisonhotesapp.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maisonhotesapp.data.database.MaisonsHotesDatabase
import com.example.maisonhotesapp.data.entity.MaisonHote
import com.example.maisonhotesapp.data.repository.MaisonRepository
import com.example.maisonhotesapp.databinding.FragmentMaisonDetailBinding
import com.example.maisonhotesapp.ui.adapter.AvisAdapter
import com.example.maisonhotesapp.ui.adapter.ImagePagerAdapter
import com.example.maisonhotesapp.ui.viewmodel.MaisonDetailViewModel

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

        // Bouton retour
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        setupObservers()
    }

    private fun setupUI(maison: MaisonHote) {
        binding.apply {
            textViewNom.text = maison.nom
            textViewDescription.text = maison.description
            textViewTelephone.text = maison.telephone
            textViewEmail.text = maison.email
            textViewPrix.text = "${maison.prix}€ / nuit"
            ratingBar.rating = maison.notation
            textViewNombreAvis.text = "(${maison.nombreAvis} avis)"
            textViewNombreChambres.text = "${maison.nombreChambres} chambres"
            textViewNombreLits.text = "${maison.nombreLits} lits"
            textViewAmenities.text = maison.amenities.replace(",", ", ")

            // Charger l'image principale
            val imageResId = if (maison.imageUrl.isNotEmpty() && !maison.imageUrl.startsWith("http")) {
                // Si c'est un nom de ressource locale (ex: "maison_tunis_1")
                val resId = requireContext().resources.getIdentifier(
                    maison.imageUrl,
                    "drawable",
                    requireContext().packageName
                )
                if (resId != 0) resId else com.example.maisonhotesapp.R.drawable.ic_placeholder
            } else if (maison.imageUrl.startsWith("http")) {
                // Si c'est une URL complète
                maison.imageUrl
            } else {
                com.example.maisonhotesapp.R.drawable.ic_placeholder
            }

            com.bumptech.glide.Glide.with(requireContext())
                .load(imageResId)
                .centerCrop()
                .placeholder(com.example.maisonhotesapp.R.drawable.ic_placeholder)
                .error(com.example.maisonhotesapp.R.drawable.ic_placeholder)
                .into(imageMaisonDetail)
        }
    }

    private fun setupObservers() {
        // Observer la maison
        viewModel.maisonHote.observe(viewLifecycleOwner) { maison ->
            maison?.let {
                setupUI(it)
                setupListeners(it)
            }
        }

        // Observer les images
        viewModel.images.observe(viewLifecycleOwner) { images ->
            val adapter = ImagePagerAdapter(images)
            binding.recyclerViewImages.adapter = adapter
            binding.recyclerViewImages.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        // Observer les avis
        viewModel.avis.observe(viewLifecycleOwner) { avis ->
            val avisAdapter = AvisAdapter()
            avisAdapter.submitList(avis)
            // Note: Vous devez ajouter une RecyclerView pour les avis dans le layout si elle n'existe pas
        }
    }

    private fun setupListeners(maison: MaisonHote) {
        binding.apply {
            // Bouton appeler
            btnAppeler.setOnClickListener {
                if (maison.telephone.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${maison.telephone}"))
                    startActivity(intent)
                }
            }

            // Bouton Site Web
            btnSiteWeb.setOnClickListener {
                if (maison.siteWeb.isNotEmpty()) {
                    openUrl(maison.siteWeb)
                }
            }

            // Bouton Facebook
            btnFacebook.setOnClickListener {
                if (maison.facebook.isNotEmpty()) {
                    openUrl(maison.facebook)
                }
            }

            // Bouton Instagram
            btnInstagram.setOnClickListener {
                if (maison.instagram.isNotEmpty()) {
                    openUrl(maison.instagram)
                }
            }

            // Bouton Maps (Itinéraire)
            btnMaps.setOnClickListener {
                val uri = Uri.parse("geo:${maison.latitude},${maison.longitude}?q=${maison.latitude},${maison.longitude}(${maison.nom})")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }

            // Bouton Favori
            btnFavorite.setOnClickListener {
                viewModel.toggleFavorite()
                btnFavorite.text = if (maison.isFavorite) "Retirer des favoris" else "Ajouter aux favoris"
            }
        }
    }

    private fun openUrl(url: String) {
        val uri = Uri.parse(if (!url.startsWith("http")) "https://$url" else url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}