
package com.artur.moodlog.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artur.moodlog.data.model.MoodEntry
import com.artur.moodlog.viewmodel.MoodViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HistoryScreen(viewModel: MoodViewModel) {

    val entries by viewModel.entries.collectAsState()

    // Состояние раскрытых карточек
    val expandedDates = remember {
        mutableStateMapOf<String, Boolean>()
    }

    val emojis = listOf("😢", "😕", "😐", "😊", "🤩")
    val names = listOf("Ужасно", "Плохо", "Нормально", "Хорошо", "Отлично")

    // Группируем все записи по датам
    val groupedEntries = entries
        .groupBy { it.date }
        .toList()
        .sortedByDescending { it.first }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF171722))
            .padding(24.dp)
    ) {

        Text(
            text = "История",
            fontSize = 28.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (entries.isEmpty()) {

            Text(
                text = "Здесь пока нет записей",
                color = Color.Gray,
                fontSize = 16.sp
            )

        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(
                    items = groupedEntries,
                    key = { it.first }
                ) { (date, dayEntries) ->

                    // Общая оценка дня
                    val dayMood = dayEntries.firstOrNull {
                        it.entryType == MoodEntry.TYPE_DAY
                    }

                    // Все моментальные записи
                    val momentEntries = dayEntries.filter {
                        it.entryType == MoodEntry.TYPE_MOMENT
                    }

                    val isExpanded = expandedDates[date] ?: false

                    val formattedDate = try {
                        LocalDate.parse(date).format(
                            DateTimeFormatter.ofPattern(
                                "d MMMM yyyy",
                                Locale("ru")
                            )
                        )
                    } catch (e: Exception) {
                        date
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                expandedDates[date] = !isExpanded
                            },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF252536)
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(18.dp)
                        ) {

                            // Заголовок карточки
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text(
                                    text = formattedDate,
                                    color = Color(0xFFB69CFF),
                                    fontSize = 16.sp
                                )

                                Text(
                                    text = if (isExpanded) "▲" else "▼",
                                    color = Color(0xFFB69CFF),
                                    fontSize = 16.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Общая оценка дня
                            if (dayMood != null) {

                                val index = dayMood.mood - 1

                                Text(
                                    text = if (index in emojis.indices) {
                                        "${emojis[index]} ${names[index]}"
                                    } else {
                                        "Неизвестное настроение"
                                    },
                                    color = Color.White,
                                    fontSize = 18.sp
                                )

                                if (dayMood.note.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = dayMood.note,
                                        color = Color.LightGray,
                                        fontSize = 14.sp
                                    )
                                }

                            } else {

                                Text(
                                    text = "Общая оценка не указана",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }

                            // Раскрываем содержимое
                            if (isExpanded) {

                                Spacer(modifier = Modifier.height(15.dp))

                                Text(
                                    text = "Настроение в течение дня",
                                    color = Color(0xFFB69CFF),
                                    fontSize = 15.sp
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                if (momentEntries.isEmpty()) {

                                    Text(
                                        text = "Моментальных записей нет",
                                        color = Color.Gray,
                                        fontSize = 14.sp
                                    )

                                } else {

                                    momentEntries.forEach { entry ->

                                        MomentEntryItem(
                                            entry = entry,
                                            emojis = emojis,
                                            names = names
                                        )

                                        Spacer(
                                            modifier = Modifier.height(12.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MomentEntryItem(
    entry: MoodEntry,
    emojis: List<String>,
    names: List<String>
) {

    val index = entry.mood - 1

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFF323247),
                RoundedCornerShape(12.dp)
            )
            .padding(14.dp)
    ) {

        Text(
            text = if (index in emojis.indices) {
                "${emojis[index]} ${names[index]}"
            } else {
                "Неизвестное настроение"
            },
            color = Color.White,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = entry.time,
            color = Color(0xFFB69CFF),
            fontSize = 13.sp
        )

        if (entry.note.isNotBlank()) {

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = entry.note,
                color = Color.LightGray,
                fontSize = 14.sp
            )
        }
    }
}
