package com.example.pottytime.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pottytime.data.Toilet
import com.example.pottytime.data.ToiletType
import com.example.pottytime.data.ToiletTypeConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Toilet::class), version = 1, exportSchema = false)
@TypeConverters(ToiletTypeConverter::class)
abstract class ToiletDatabase : RoomDatabase() {

    abstract fun toiletDao(): ToiletDao

    private class ToiletDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.toiletDao())
                }
            }
        }

        suspend fun populateDatabase(toiletDao: ToiletDao) {

            // Delete all content here.
            toiletDao.deleteAll()

            // Add sample toilet.
            var toilet = Toilet(null, "Teszt meki" , 47.3560812, 18.9966442 ,"01234567",ToiletType.MCDONALDS );
            toiletDao.insert(toilet);

            toilet = Toilet(null, "Teszt burger" , 47.3560812, 18.9966442 ,"01234567",ToiletType.BURGERKING );
            toiletDao.insert(toilet);

            toilet = Toilet(null, "Teszt kfc" , 47.3560812, 18.9966442 ,"01234567",ToiletType.KFC );
            toiletDao.insert(toilet);

            toilet = Toilet(null, "Teszt más" , 47.3560812, 18.9966442 ,"01234567",ToiletType.OTHER );
            toiletDao.insert(toilet);

        }
    }

    companion object {
        // Singleton pattern
        @Volatile
        private var INSTANCE: ToiletDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ToiletDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToiletDatabase::class.java,
                    "toilet_database"
                ).addCallback(ToiletDatabaseCallback(scope)).build()

                INSTANCE = instance
                return instance
            }
        }
    }

}