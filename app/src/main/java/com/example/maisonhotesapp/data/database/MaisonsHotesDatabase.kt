package com.example.maisonhotes.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.maisonhotes.data.dao.*
import com.example.maisonhotes.data.entity.*

@Database(
    entities = [
        Region::class,
        Ville::class,
        MaisonHote::class,
        Avis::class,
        Image::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MaisonsHotesDatabase : RoomDatabase() {

    abstract fun regionDao(): RegionDao
    abstract fun villeDao(): VilleDao
    abstract fun maisonHoteDao(): MaisonHoteDao
    abstract fun avisDao(): AvisDao
    abstract fun imageDao(): ImageDao

    companion object {
        @Volatile
        private var INSTANCE: MaisonsHotesDatabase? = null

        fun getInstance(context: Context): MaisonsHotesDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MaisonsHotesDatabase::class.java,
                    "maisons_hotes.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}