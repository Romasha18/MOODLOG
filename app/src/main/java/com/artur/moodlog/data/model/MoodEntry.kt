
package com.artur.moodlog.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "mood_entries",
    indices = [Index(value = ["date"])]
)
data class MoodEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val date: String,

    val mood: Int,

    val note: String = "",

    @ColumnInfo(defaultValue = "''")
    val time: String = "",

    @ColumnInfo(defaultValue = "'DAY'")
    val entryType: String = TYPE_MOMENT
) {
    companion object {
        const val TYPE_DAY = "DAY"
        const val TYPE_MOMENT = "MOMENT"
    }
}
