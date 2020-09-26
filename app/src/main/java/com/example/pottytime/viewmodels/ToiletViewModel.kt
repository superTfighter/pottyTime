package com.example.pottytime.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pottytime.data.Toilet
import com.example.pottytime.database.ToiletDatabase
import com.example.pottytime.repositories.ToiletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToiletViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ToiletRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Toilet>>

    init {
        val wordsDao = ToiletDatabase.getDatabase(application, viewModelScope).toiletDao()
        repository = ToiletRepository(wordsDao)
        allWords = repository.allWords
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(toilet: Toilet) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(toilet)
    }
}