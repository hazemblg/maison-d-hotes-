package com.example.maisonhotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.maisonhotes.data.DataInitializer
import com.example.maisonhotes.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialiser ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialiser la base de données
        lifecycleScope.launch {
            DataInitializer.initializeDatabase(this@MainActivity)
        }

        // Configurer la navigation
        setupNavigation()
    }

    private fun setupNavigation() {
        // Récupérer le NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        // Récupérer le NavController
        navController = navHostFragment.navController

        // Connecter BottomNavigationView avec NavController
        binding.bottomNav.setupWithNavController(navController)
    }
}