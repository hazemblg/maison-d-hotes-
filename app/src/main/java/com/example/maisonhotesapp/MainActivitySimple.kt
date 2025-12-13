package com.example.maisonhotesapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.maisonhotesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Version ULTRA-SIMPLIFIÉE de MainActivity pour debugging
 * Utilisez cette version si l'app continue de crasher
 */
class MainActivitySimple : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("MainActivitySimple", "=== STARTING SIMPLE VERSION ===")

        try {
            // 1. Initialiser le binding
            _binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            Log.d("MainActivitySimple", "✓ Binding OK")

            // 2. Background basique
            try {
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(this, android.R.color.white)
                )
                Log.d("MainActivitySimple", "✓ Background OK")
            } catch (e: Exception) {
                Log.e("MainActivitySimple", "✗ Background FAILED", e)
            }

            // 3. Setup navigation
            try {
                val navHostFragment = supportFragmentManager
                    .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment

                if (navHostFragment != null) {
                    val navController = navHostFragment.navController
                    binding.bottomNav.setupWithNavController(navController)
                    Log.d("MainActivitySimple", "✓ Navigation OK")

                    Toast.makeText(this, "Navigation configurée", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("MainActivitySimple", "✗ NavHostFragment NOT FOUND")
                    Toast.makeText(this, "Erreur: NavHostFragment introuvable", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Log.e("MainActivitySimple", "✗ Navigation FAILED", e)
                Toast.makeText(this, "Erreur navigation: ${e.message}", Toast.LENGTH_LONG).show()
            }

            // 4. Database en arrière-plan (optionnel)
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    // Ne charger la DB que si absolument nécessaire
                    Log.d("MainActivitySimple", "⏳ Database init skipped (pour debugging)")
                } catch (e: Exception) {
                    Log.e("MainActivitySimple", "✗ Database FAILED", e)
                }
            }

            Log.d("MainActivitySimple", "=== INIT COMPLETED ===")

        } catch (e: Exception) {
            Log.e("MainActivitySimple", "=== CRITICAL ERROR ===", e)
            e.printStackTrace()
            Toast.makeText(
                this,
                "Erreur critique: ${e.javaClass.simpleName}: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        Log.d("MainActivitySimple", "=== DESTROYED ===")
    }
}

