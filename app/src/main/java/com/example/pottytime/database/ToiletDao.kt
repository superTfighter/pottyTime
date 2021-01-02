package com.example.pottytime.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pottytime.data.Toilet

@Dao
interface ToiletDao {

    @Query("SELECT * from toilet_table ORDER BY id ASC")
    fun getAll(): LiveData<List<Toilet>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(toilet: Toilet)

    @Query("DELETE FROM toilet_table")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(toilet: Toilet)
}