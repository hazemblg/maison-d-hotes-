package com.example.maisonhotesapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.maisonhotesapp.data.dao.*
import com.example.maisonhotesapp.data.entity.*

@Database(
    entities = [
        Region::class,
        Ville::class,
        MaisonHote::class,
        Image::class,
        Avis::class
    ],
    version = 4,
    exportSchema = false
)
abstract class MaisonsHotesDatabase : RoomDatabase() {

    abstract fun regionDao(): RegionDao
    abstract fun villeDao(): VilleDao
    abstract fun maisonHoteDao(): MaisonHoteDao
    abstract fun imageDao(): ImageDao
    abstract fun avisDao(): AvisDao

    companion object {
        @Volatile
        private var INSTANCE: MaisonsHotesDatabase? = null

        fun getInstance(context: Context): MaisonsHotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MaisonsHotesDatabase::class.java,
                    "maisons_hotes_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

