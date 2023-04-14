package com.example.urbandictionaryn.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UrbanDictionaryDao {
    @Query("SELECT * FROM DatabaseEntity WHERE word LIKE :searchedWord")
    suspend fun getDefinitionsByWord(searchedWord: String): List<DatabaseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordDefinition(vararg databaseEntity: DatabaseEntity)
}