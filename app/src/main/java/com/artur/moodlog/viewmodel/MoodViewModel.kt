
package com.artur.moodlog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artur.moodlog.data.model.MoodEntry
import com.artur.moodlog.data.repository.MoodRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MoodViewModel(
    private val repository: MoodRepository
) : ViewModel() {

    // Все записи пользователя
    val entries = repository.getAllEntries()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Получение общей оценки за конкретный день
    suspend fun getEntryByDate(date: String): MoodEntry? {
        return repository.getEntryByDate(date)
    }

    // Получение моментальных записей за конкретный день
    fun getMomentEntriesByDate(date: String) =
        repository.getMomentEntriesByDate(date)

    // Сохранение или обновление общей оценки за день
    fun saveDayMood(mood: Int, note: String) {
        viewModelScope.launch {
            val today = LocalDate.now().toString()

            // Проверяем, существует ли уже оценка за сегодня
            val existingEntry = repository.getEntryByDate(today)

            val entry = MoodEntry(
                id = existingEntry?.id ?: 0,
                date = today,
                mood = mood,
                note = note,
                time = "",
                entryType = MoodEntry.TYPE_DAY
            )

            repository.insertEntry(entry)
        }
    }

    // Сохранение моментального настроения
    fun saveMomentMood(mood: Int, note: String) {
        viewModelScope.launch {
            val today = LocalDate.now().toString()
            val currentTime = LocalTime.now()
                .format(DateTimeFormatter.ofPattern("HH:mm"))

            val entry = MoodEntry(
                date = today,
                mood = mood,
                note = note,
                time = currentTime,
                entryType = MoodEntry.TYPE_MOMENT
            )

            repository.insertEntry(entry)
        }
    }

    // Удаление записи
    fun deleteEntry(id: Int) {
        viewModelScope.launch {
            repository.deleteEntry(id)
        }
    }
}
