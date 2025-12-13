package com.example.maisonhotesapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.maisonhotesapp.databinding.FragmentParametresBinding

class ParametresFragment : Fragment() {

    private lateinit var binding: FragmentParametresBinding
    private lateinit var prefs: android.content.SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentParametresBinding.inflate(inflater, container, false)
        prefs = requireContext().getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Masquer la section Apparence (mode sombre/clair désactivé)
        hideThemeSection()

        // Charger les paramètres actuels
        loadSettings()

        // Configurer les listeners
        setupListeners()
    }

    private fun hideThemeSection() {
        // Masquer les switches de thème
        try {
            binding.switchDarkMode.visibility = View.GONE
            binding.switchAutoMode.visibility = View.GONE

            // Chercher le parent CardView de la section Apparence et le masquer
            var parent = binding.switchDarkMode.parent
            while (parent != null && parent !is com.google.android.material.card.MaterialCardView) {
                parent = parent.parent as? ViewGroup
            }
            // Cast en View pour accéder à visibility
            (parent as? View)?.visibility = View.GONE
        } catch (e: Exception) {
            // Si erreur, au moins désactiver les switches
            binding.switchDarkMode.isEnabled = false
            binding.switchAutoMode.isEnabled = false
        }
    }

    private fun loadSettings() {
        // Mode sombre - DÉSACTIVÉ
        // binding.switchDarkMode.isChecked = ThemeManager.isDarkModeEnabled(requireContext())

        // Mode automatique - DÉSACTIVÉ
        // binding.switchAutoMode.isChecked = ThemeManager.isAutoModeEnabled(requireContext())

        // Notifications
        binding.switchNotifications.isChecked = prefs.getBoolean("notifications_enabled", true)

        // Désactiver le switch mode sombre si le mode auto est activé
        // binding.switchDarkMode.isEnabled = !binding.switchAutoMode.isChecked
    }

    private fun setupListeners() {
        // Switch Mode Sombre - DÉSACTIVÉ
        // binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
        //     ThemeManager.setDarkMode(requireContext(), isChecked)
        //     showToast(if (isChecked) "Mode sombre activé" else "Mode clair activé")
        // }

        // Switch Mode Automatique - DÉSACTIVÉ
        // binding.switchAutoMode.setOnCheckedChangeListener { _, isChecked ->
        //     ThemeManager.setAutoMode(requireContext(), isChecked)
        //     binding.switchDarkMode.isEnabled = !isChecked
        //     showToast(if (isChecked) "Mode automatique activé" else "Mode automatique désactivé")
        // }

        // Switch Notifications
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("notifications_enabled", isChecked).apply()
            showToast(if (isChecked) "Notifications activées" else "Notifications désactivées")
        }

        // Bouton Politique de confidentialité
        binding.btnPrivacyPolicy.setOnClickListener {
            showToast("Politique de confidentialité")
        }

        // Bouton Conditions d'utilisation
        binding.btnTerms.setOnClickListener {
            showToast("Conditions d'utilisation")
        }

        // Bouton Reset
        binding.btnResetSettings.setOnClickListener {
            resetSettings()
        }
    }

    private fun resetSettings() {
        // Réinitialiser tous les paramètres (sans toucher au thème)
        // ThemeManager.setAutoMode(requireContext(), true)
        // ThemeManager.setDarkMode(requireContext(), false)
        prefs.edit().clear().apply()

        // Recharger les paramètres
        loadSettings()

        showToast("Paramètres réinitialisés")
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}