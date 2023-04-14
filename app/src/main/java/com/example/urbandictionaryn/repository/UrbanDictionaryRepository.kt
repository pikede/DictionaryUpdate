package com.example.urbandictionaryn.repository

import android.util.Log
import com.example.urbandictionaryn.database.DatabaseEntity
import com.example.urbandictionaryn.database.UrbanDictionaryDatabase
import com.example.urbandictionaryn.models.WordDefinitions
import com.example.urbandictionaryn.repository.service.DictionaryService
import com.example.urbandictionaryn.utils.listFromJson
import com.example.urbandictionaryn.utils.listToJson

class UrbanDictionaryRepository(
    private val urbanDictionaryDatabase: UrbanDictionaryDatabase
) {

    private var isRetrofitRequestInProgress = false
    private var databaseDefinitions: List<WordDefinitions> = emptyList()

    //takes response from database (if not empty) or from api if database is empty
    suspend fun getSearchResults(enteredWord: String): List<WordDefinitions> {
        try {
            databaseDefinitions = getDefinitionsFromDatabase(enteredWord)
            Log.d("**logged fetching definition ", enteredWord)
        } catch (e: Exception) {
            getDefinitionsFromDatabase(enteredWord)
            Log.e("**logged ERROR GETTING DATA ", e.message ?: e.toString())
        }
        return databaseDefinitions
    }

    //gets word definitions from api call, if no current service call is in progress
    private suspend fun getDefinitionsFromServiceCall(enteredWord: String) {
        if (isRetrofitRequestInProgress) return
        else {
            isRetrofitRequestInProgress = true
            val response = DictionaryService.urbanDictionaryRetrofit
                .getWordDefinitions(enteredWord)

            databaseDefinitions = if (response.isSuccessful) {
                response.body()?.list ?: listOf()
            } else {
                emptyList()
            }

            isRetrofitRequestInProgress = false
        }
    }

    // gets definition from database if present, else make the network call
    private suspend fun getDefinitionsFromDatabase(enteredWord: String): List<WordDefinitions> {
        val databaseData = urbanDictionaryDatabase.wordDefinitionDao()
            .getDefinitionsByWord(enteredWord.lowercase())
        return if (databaseData.isNotEmpty()) {
            databaseData[0].run { listFromJson(this.json) }
        } else {
            getDefinitionsFromServiceCall(enteredWord)
            insertDefinitionsIntoDatabase()
            databaseDefinitions
        }
    }

    // inserts definition into database
    private suspend fun insertDefinitionsIntoDatabase() {
        if (databaseDefinitions.isEmpty()) return
        else {
            val wordDefinitions = databaseDefinitions[0].word.lowercase()
            val jsonStringData = listToJson(databaseDefinitions)
            urbanDictionaryDatabase.wordDefinitionDao()
                .insertWordDefinition(DatabaseEntity(wordDefinitions, jsonStringData))
        }
    }
}