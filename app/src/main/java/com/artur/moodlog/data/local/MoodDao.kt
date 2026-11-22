
package com.artur.moodlog.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.artur.moodlog.data.model.MoodEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDao {

    // Получить все записи для истории
    @Query("SELECT * FROM mood_entries ORDER BY date DESC, time DESC, id DESC")
    fun getAllEntries(): Flow<List<MoodEntry>>

    // Получить общую оценку конкретного дня
    @Query("""
        SELECT * FROM mood_entries
        WHERE date = :date AND entryType = 'DAY'
        LIMIT 1
    """)
    suspend fun getEntryByDate(date: String): MoodEntry?

    // Получить все настроения за конкретный день
    @Query("""
        SELECT * FROM mood_entries
        WHERE date = :date AND entryType = 'MOMENT'
        ORDER BY time DESC, id DESC
    """)
    fun getMomentEntriesByDate(date: String): Flow<List<MoodEntry>>

    // Добавить или заменить запись
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: MoodEntry)

    // Удалить запись
    @Query("DELETE FROM mood_entries WHERE id = :id")
    suspend fun deleteEntry(id: Int)
}
