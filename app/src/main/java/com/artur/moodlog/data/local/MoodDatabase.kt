package com.artur.moodlog.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.artur.moodlog.data.model.MoodEntry

@Database(
    entities = [MoodEntry::class],
    version = 1,
    exportSchema = false
)
abstract class MoodDatabase : RoomDatabase() {

    abstract fun moodDao(): MoodDao

    companion object {

        @Volatile
        private var INSTANCE: MoodDatabase? = null

        fun getDatabase(context: Context): MoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoodDatabase::class.java,
                    "moodlog_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}