package com.example.urbandictionaryn.dependencyinjection

import com.example.urbandictionaryn.database.UrbanDictionaryDatabase
import com.example.urbandictionaryn.repository.UrbanDictionaryRepository
import com.example.urbandictionaryn.repository.service.DictionaryService
import com.example.urbandictionaryn.viewmodel.UrbanDictionaryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val DictionaryModule = module {
    single { UrbanDictionaryDatabase.getDataBase(androidContext()) }
    single { DictionaryService() }
    single { UrbanDictionaryRepository(get()) }
    viewModel { UrbanDictionaryViewModel(get()) }
}
