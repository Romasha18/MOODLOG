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
import com.artur.moodlog.viewmodel.MoodViewModel

@Composable
fun StatisticsScreen(viewModel: MoodViewModel) {
    val entries by viewModel.entries.collectAsState()

    val average = if (entries.isNotEmpty()) {
        entries.map { it.mood }.average()
    } else {
        0.0
    }

    val mostFrequent = entries
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

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF252536)
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Всего записей",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Text(
                    text = entries.size.toString(),
                    color = Color.White,
                    fontSize = 36.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF252536)
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Среднее настроение",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Text(
                    text = if (entries.isEmpty()) "Нет данных"
                    else String.format("%.1f / 5", average),
                    color = Color.White,
                    fontSize = 28.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF252536)
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Самое частое настроение",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Text(
                    text = if (mostFrequent != null) {
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