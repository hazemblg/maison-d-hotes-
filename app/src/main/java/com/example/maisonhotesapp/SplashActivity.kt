package com.example.maisonhotesapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.maisonhotesapp.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!
    private var hasNavigated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("SplashActivity", "onCreate started")

        // Désactivé : ThemeManager ne sera plus appelé
        // try {
        //     ThemeManager.applyTheme(this)
        // } catch (e: Exception) {
        //     Log.e("SplashActivity", "Error applying theme", e)
        // }

        super.onCreate(savedInstanceState)

        try {
            _binding = ActivitySplashBinding.inflate(layoutInflater)
            setContentView(binding.root)

            // Afficher tous les éléments immédiatement
            showAllElements()

            // Clic sur la flèche
            binding.arrowButton.setOnClickListener {
                navigateToMain()
            }

            // Auto-navigation après 2 secondes
            lifecycleScope.launch {
                delay(2000)
                navigateToMain()
            }

            Log.d("SplashActivity", "onCreate completed")
        } catch (e: Exception) {
            Log.e("SplashActivity", "Critical error", e)
            e.printStackTrace()
            // Si le splash crash, aller directement à MainActivity
            navigateToMainDirect()
        }
    }

    private fun showAllElements() {
        try {
            binding.iconMaison.alpha = 1f
            binding.splashTitle.alpha = 1f
            binding.splashSubtitle.alpha = 1f
            binding.splashDescription.alpha = 1f
            binding.buttonText.alpha = 1f
            binding.arrowButton.alpha = 1f
        } catch (e: Exception) {
            Log.e("SplashActivity", "Error showing elements", e)
        }
    }

    private fun navigateToMain() {
        if (!hasNavigated) {
            hasNavigated = true
            Log.d("SplashActivity", "Navigating to MainActivity")
            navigateToMainDirect()
        }
    }

    private fun navigateToMainDirect() {
        try {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Log.e("SplashActivity", "Error navigating to MainActivity", e)
            e.printStackTrace()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        Log.d("SplashActivity", "onDestroy")
    }
}
