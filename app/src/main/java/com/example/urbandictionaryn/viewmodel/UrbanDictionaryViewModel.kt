package com.example.urbandictionaryn.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urbandictionaryn.models.WordDefinitions
import com.example.urbandictionaryn.repository.UrbanDictionaryRepository
import com.example.urbandictionaryn.utils.SortTypeEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UrbanDictionaryViewModel(
    private val urbanDictionaryRepository: UrbanDictionaryRepository
) : ViewModel() {

    private val _wordDefinitions = MutableLiveData<List<WordDefinitions>>()
    val wordDefinitions: LiveData<List<WordDefinitions>> get() = _wordDefinitions

    private val _wordDetail = MutableLiveData<WordDefinitions>()
    val wordDetail: LiveData<WordDefinitions> get() = _wordDetail

    val errorMsg = MutableLiveData<String>()

    fun getWordDefinitions(enteredWord: String) = viewModelScope.launch(Dispatchers.IO) {
        val definition = urbanDictionaryRepository.getSearchResults(enteredWord)
        _wordDefinitions.postValue(
            definition.ifEmpty {
                handleErrorResponse()
                // handles error scenario as empty list (like no internet, invalid word, etc.)
                emptyList()
            }
        )

    }

    // handle error scenario
    private fun handleErrorResponse() {
        errorMsg.postValue("Error fetching word meaning")
    }

    fun clearWordDefinition() {
        viewModelScope.launch(Dispatchers.IO) {
            _wordDefinitions.postValue(listOf())
            Log.d("**logged", "clearing definitions in viewmodel")
        }
    }

    // sorts word definitions and returns back a sorted list
    fun sortDefinitions(
        sortType: SortTypeEnum
    ) {
        // sort on Dispatcher IO as this list might be very large
        viewModelScope.launch(Dispatchers.IO) {
            _wordDefinitions.value?.let {
                // sorts by thumbs up or thumbs down
                val map = mutableMapOf<Int, WordDefinitions>()
                when (sortType) {
                    SortTypeEnum.UP -> {
                        for (word in it) {
                            map[word.thumbsUp] = word
                        }
                    }

                    SortTypeEnum.DOWN -> {
                        for (word in it) {
                            map[word.thumbsDown] = word
                        }
                    }
                }

                // reverses increasing order
                _wordDefinitions.postValue(map.toSortedMap().values.reversed())
            }
        }
    }

    fun getWordDetails(position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _wordDefinitions.value?.let {
                _wordDetail.postValue(it[position])
                Log.d("**logged", "position: $position")
                Log.d("**logged", "word name ${it[position].word}")
            }
        }
    }
}