package com.artur.moodlog.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun HistoryScreen(viewModel: MoodViewModel) {
    val entries by viewModel.entries.collectAsState()

    val emojis = listOf("😢", "😕", "😐", "😊", "🤩")
    val names = listOf("Ужасно", "Плохо", "Нормально", "Хорошо", "Отлично")

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
                items(entries) { entry ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF252536)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp)
                        ) {
                            Text(
                                text = "${emojis[entry.mood - 1]}  ${names[entry.mood - 1]}",
                                color = Color.White,
                                fontSize = 18.sp
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = entry.date,
                                color = Color(0xFFB69CFF),
                                fontSize = 13.sp
                            )

                            if (entry.note.isNotBlank()) {
                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = entry.note,
                                    color = Color.LightGray,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}