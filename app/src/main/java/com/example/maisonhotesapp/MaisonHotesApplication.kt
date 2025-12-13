package com.example.maisonhotesapp

import android.app.Application
import android.util.Log
import com.example.maisonhotesapp.data.DataInitializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MaisonHotesApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        Log.d("MaisonHotesApp", "Application started - Initializing database...")

        // Initialiser la base de données au démarrage de l'application
        applicationScope.launch {
            try {
                DataInitializer.initializeDatabase(this@MaisonHotesApplication)
                Log.d("MaisonHotesApp", "✅ Database initialized successfully")
            } catch (e: Exception) {
                Log.e("MaisonHotesApp", "❌ Database initialization failed", e)
                e.printStackTrace()
            }
        }
    }
}
