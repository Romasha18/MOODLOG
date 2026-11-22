package com.artur.moodlog.data.repository

import com.artur.moodlog.data.local.MoodDao
import com.artur.moodlog.data.model.MoodEntry
import kotlinx.coroutines.flow.Flow

class MoodRepository(
    private val moodDao: MoodDao
) {

    fun getAllEntries(): Flow<List<MoodEntry>> {
        return moodDao.getAllEntries()
    }

    suspend fun getEntryByDate(date: String): MoodEntry? {
        return moodDao.getEntryByDate(date)
    }

    suspend fun insertEntry(entry: MoodEntry) {
        moodDao.insertEntry(entry)
    }

    suspend fun deleteEntry(id: Int) {
        moodDao.deleteEntry(id)
    }
}