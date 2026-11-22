
package com.artur.moodlog.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artur.moodlog.data.model.MoodEntry
import com.artur.moodlog.viewmodel.MoodViewModel

@Composable
fun StatisticsScreen(viewModel: MoodViewModel) {

    val entries by viewModel.entries.collectAsState()

    // Разделяем записи по типам
    val dayEntries = entries.filter {
        it.entryType == MoodEntry.TYPE_DAY
    }

    val momentEntries = entries.filter {
        it.entryType == MoodEntry.TYPE_MOMENT
    }

    // Среднее настроение рассчитываем только по оценкам дней
    val average = if (dayEntries.isNotEmpty()) {
        dayEntries.map { it.mood }.average()
    } else {
        0.0
    }

    // Самое частое настроение среди общих оценок
    val mostFrequent = dayEntries
        .groupingBy { it.mood }
        .eachCount()
        .maxByOrNull { it.value }
        ?.key

    val emojis = listOf("😢", "😕", "😐", "😊", "🤩")
    val names = listOf("Ужасно", "Плохо", "Нормально", "Хорошо", "Отлично")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF171722))
            .padding(24.dp)
    ) {

        Text(
            text = "Статистика",
            fontSize = 28.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(25.dp))

        // Общие оценки и моментальные записи
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            StatisticCard(
                title = "Оценки дней",
                value = dayEntries.size.toString(),
                modifier = Modifier.weight(1f)
            )

            StatisticCard(
                title = "Моментальные",
                value = momentEntries.size.toString(),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Среднее настроение
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF252536)
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Среднее настроение",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (dayEntries.isEmpty()) {
                        "Нет данных"
                    } else {
                        String.format("%.1f / 5", average)
                    },
                    color = Color.White,
                    fontSize = 28.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Самое частое настроение
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF252536)
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Самое частое настроение",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (mostFrequent != null &&
                        mostFrequent in 1..5
                    ) {
                        "${emojis[mostFrequent - 1]} ${names[mostFrequent - 1]}"
                    } else {
                        "Нет данных"
                    },
                    color = Color.White,
                    fontSize = 22.sp
                )
            }
        }
    }
}

@Composable
private fun StatisticCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF252536)
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = title,
                color = Color.Gray,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                color = Color.White,
                fontSize = 30.sp
            )
        }
    }
}
