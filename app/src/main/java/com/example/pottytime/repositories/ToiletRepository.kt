package com.example.pottytime.repositories

import androidx.lifecycle.LiveData
import com.example.pottytime.data.Toilet
import com.example.pottytime.database.ToiletDao

class ToiletRepository(private val toiletDao: ToiletDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allWords: LiveData<List<Toilet>> = toiletDao.getAll()

    suspend fun insert(toilet: Toilet) {
        toiletDao.insert(toilet)
    }

    suspend fun delete(toilet: Toilet){
        toiletDao.delete(toilet);
    }
}