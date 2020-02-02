package com.srjhnd.freenews.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Headline::class], version = 2)
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

        private val MIGRATE_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE headlines_table ADD COLUMN timestamp INTEGER")
            }
        }

        private fun buildDatabase(context: Context): ApplicationDatabase {
            return Room.databaseBuilder(
                context,
                ApplicationDatabase::class.java,
                "application_database"
            )
                .addMigrations(MIGRATE_1_2)
                .fallbackToDestructiveMigration()
                .build()
        }

    }
}