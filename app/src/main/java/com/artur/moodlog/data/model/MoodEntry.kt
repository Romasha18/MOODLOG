package com.artur.moodlog.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "mood_entries",
    indices = [Index(value = ["date"], unique = true)]
)
data class MoodEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val date: String,

    val mood: Int,

    val note: String = ""
)