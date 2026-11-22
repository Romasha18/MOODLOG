
package com.artur.moodlog.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.artur.moodlog.data.model.MoodEntry

@Database(
    entities = [MoodEntry::class],
    version = 2,
    exportSchema = false
)
abstract class MoodDatabase : RoomDatabase() {

    abstract fun moodDao(): MoodDao

    companion object {

        @Volatile
        private var INSTANCE: MoodDatabase? = null

        // Миграция с версии 1 на версию 2
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {

                // Добавляем время записи
                db.execSQL(
                    "ALTER TABLE mood_entries ADD COLUMN time TEXT NOT NULL DEFAULT ''"
                )

                // Старые записи считаем общей оценкой дня
                db.execSQL(
                    "ALTER TABLE mood_entries ADD COLUMN entryType TEXT NOT NULL DEFAULT 'DAY'"
                )

                // Удаляем старый уникальный индекс
                db.execSQL(
                    "DROP INDEX IF EXISTS index_mood_entries_date"
                )

                // Создаём обычный индекс без ограничения уникальности
                db.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_mood_entries_date ON mood_entries(date)"
                )
            }
        }

        fun getDatabase(context: Context): MoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoodDatabase::class.java,
                    "moodlog_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
