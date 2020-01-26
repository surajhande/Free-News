package com.srjhnd.freenews.data

import android.content.Context
import androidx.room.*

@Database(entities = [Headline::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun headlineDAO(): HeadlineDAO

    companion object {
        @Volatile
        private var instance: ApplicationDatabase? = null

        fun getInstance(context: Context): ApplicationDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ApplicationDatabase {
            return Room.databaseBuilder(
                context,
                ApplicationDatabase::class.java,
                "application_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}