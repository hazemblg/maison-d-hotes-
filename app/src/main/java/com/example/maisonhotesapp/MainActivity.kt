package com.example.maisonhotesapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.maisonhotesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "onCreate started")

        // Désactivé : ThemeManager ne sera plus appelé
        // try {
        //     ThemeManager.applyTheme(this)
        // } catch (e: Exception) {
        //     Log.e("MainActivity", "Error applying theme", e)
        // }

        super.onCreate(savedInstanceState)

        try {
            // Initialiser ViewBinding
            _binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            Log.d("MainActivity", "Binding initialized")

            // Appliquer un background simple
            applyBackground()

            // Configurer la navigation
            setupNavigation()

            // Initialiser la base de données en arrière-plan (optionnel)
            initializeDatabase()

            Log.d("MainActivity", "onCreate completed successfully")
        } catch (e: Exception) {
            Log.e("MainActivity", "Critical error in onCreate", e)
            e.printStackTrace()
            // Ne pas crasher, continuer avec UI minimale
        }
    }

    private fun setupNavigation() {
        try {
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment

            if (navHostFragment != null) {
                navController = navHostFragment.navController
                binding.bottomNav.setupWithNavController(navController!!)
                Log.d("MainActivity", "Navigation setup successful")
            } else {
                Log.e("MainActivity", "NavHostFragment not found")
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error setting up navigation", e)
            e.printStackTrace()
        }
    }

    private fun applyBackground() {
        try {
            binding.root.background = ContextCompat.getDrawable(
                this,
                R.drawable.bg_gradient_blue_dynamic
            )
            Log.d("MainActivity", "Background applied")
        } catch (e: Exception) {
            Log.e("MainActivity", "Error applying background", e)
            // Fallback: utiliser une couleur simple
            try {
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(this, android.R.color.white)
                )
            } catch (ex: Exception) {
                Log.e("MainActivity", "Error applying fallback background", ex)
            }
        }
    }

    private fun initializeDatabase() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                com.example.maisonhotesapp.data.DataInitializer.initializeDatabase(this@MainActivity)
                Log.d("MainActivity", "Database initialized successfully")
            } catch (e: Exception) {
                Log.e("MainActivity", "Database initialization failed", e)
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        Log.d("MainActivity", "onDestroy")
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp() ?: false || super.onSupportNavigateUp()
    }
}