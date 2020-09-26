package com.example.pottytime.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "toilet_table")
data class Toilet(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "lat") val latitude : Double,
    @ColumnInfo(name = "lon") val longitude : Double,
    @ColumnInfo(name = "code") val code : String
)