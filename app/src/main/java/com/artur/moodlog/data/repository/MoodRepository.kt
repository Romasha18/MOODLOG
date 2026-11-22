
package com.artur.moodlog.data.repository

import com.artur.moodlog.data.local.MoodDao
import com.artur.moodlog.data.model.MoodEntry
import kotlinx.coroutines.flow.Flow

class MoodRepository(
    private val moodDao: MoodDao
) {

    // Все записи для истории
    fun getAllEntries(): Flow<List<MoodEntry>> {
        return moodDao.getAllEntries()
    }

    // Общая оценка конкретного дня
    suspend fun getEntryByDate(date: String): MoodEntry? {
        return moodDao.getEntryByDate(date)
    }

    // Все моментальные настроения за день
    fun getMomentEntriesByDate(date: String): Flow<List<MoodEntry>> {
        return moodDao.getMomentEntriesByDate(date)
    }

    // Сохранение записи
    suspend fun insertEntry(entry: MoodEntry) {
        moodDao.insertEntry(entry)
    }

    // Удаление записи
    suspend fun deleteEntry(id: Int) {
        moodDao.deleteEntry(id)
    }
}
