package com.example.pottytime.data

import android.util.Log
import androidx.room.*
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "toilet_table")
data class Toilet(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "lat") val latitude : Double?,
    @ColumnInfo(name = "lon") val longitude : Double?,
    @ColumnInfo(name = "code") val code : String,
    @ColumnInfo(name = "type") val type : ToiletType?
)

@Serializable
enum class ToiletType(private val value: Int) {
    OTHER(0),
    MCDONALDS(1),
    BURGERKING(2),
    KFC(3);

    open fun getValue() :  Int {
        return this.value;
    }

}

class ToiletTypeConverter{

    @TypeConverter
    fun fromToiletTypeToInt(value : ToiletType) : Int{
        return value.getValue()
    }

    @TypeConverter
    fun fromIntToToiletType(value : Int) : ToiletType{
        return ToiletType.values()[value];
    }

}