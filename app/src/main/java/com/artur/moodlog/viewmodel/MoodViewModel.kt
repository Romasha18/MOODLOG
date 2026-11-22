package com.artur.moodlog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artur.moodlog.data.model.MoodEntry
import com.artur.moodlog.data.repository.MoodRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

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

    // Получение записи за конкретную дату
    suspend fun getEntryByDate(date: String): MoodEntry? {
        return repository.getEntryByDate(date)
    }

    // Сохранение настроения
    fun saveMood(mood: Int, note: String) {
        viewModelScope.launch {
            val today = LocalDate.now().toString()

            val entry = MoodEntry(
                date = today,
                mood = mood,
                note = note
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