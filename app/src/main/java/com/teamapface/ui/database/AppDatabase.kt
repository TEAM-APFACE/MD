package com.teamapface.utils.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.teamapface.utils.model.SavedResult

@Database(entities = [SavedResult::class], version = 2, exportSchema = false) // Increment version number
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedResultDao(): SavedResultDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "saved_results_db"
                )
                    //.fallbackToDestructiveMigration() // Add this line
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
