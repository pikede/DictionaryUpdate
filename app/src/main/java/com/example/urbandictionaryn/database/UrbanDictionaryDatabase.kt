package com.example.urbandictionaryn.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseEntity::class], version = 1, exportSchema = false)
abstract class UrbanDictionaryDatabase : RoomDatabase() {

    abstract fun wordDefinitionDao(): UrbanDictionaryDao

    companion object {
        private const val databaseName = "urban_dictionary"

        @Volatile
        private var INSTANCE: UrbanDictionaryDatabase? = null

        fun getDataBase(context: Context): UrbanDictionaryDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            UrbanDictionaryDatabase::class.java,
            databaseName
        ).build()
    }
}