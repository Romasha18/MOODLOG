package com.artur.moodlog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MoodOption(
    val value: Int,
    val emoji: String,
    val title: String,
    val color: Color
)

private val moods = listOf(
    MoodOption(1, "😢", "Ужасно", Color(0xFFEF5350)),
    MoodOption(2, "😕", "Плохо", Color(0xFFFF9800)),
    MoodOption(3, "😐", "Нормально", Color(0xFFFFCA28)),
    MoodOption(4, "😊", "Хорошо", Color(0xFF66BB6A)),
    MoodOption(5, "🤩", "Отлично", Color(0xFF42A5F5))
)

@Composable
fun MoodSelector(
    selectedMood: Int,
    onMoodSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        moods.forEach { mood ->
            val isSelected = selectedMood == mood.value

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 3.dp)
                    .background(
                        color = if (isSelected) {
                            mood.color.copy(alpha = 0.2f)
                        } else {
                            Color(0xFF252536)
                        },
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable {
                        onMoodSelected(mood.value)
                    }
                    .padding(vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = mood.emoji,
                    fontSize = 25.sp
                )

                Text(
                    text = mood.title,
                    fontSize = 10.sp,
                    color = if (isSelected) mood.color else Color.LightGray
                )
            }
        }
    }
}