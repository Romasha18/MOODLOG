
package com.artur.moodlog.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artur.moodlog.ui.components.MoodSelector
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HomeScreen(
    onSaveDayMood: (Int, String) -> Unit,
    onSaveMomentMood: (Int, String) -> Unit
) {
    var selectedMood by remember { mutableIntStateOf(0) }
    var note by remember { mutableStateOf("") }
    var saved by remember { mutableStateOf(false) }

    // Выбранный тип записи
    var isDayMode by remember { mutableStateOf(true) }

    val today = LocalDate.now().format(
        DateTimeFormatter.ofPattern(
            "d MMMM yyyy",
            Locale("ru")
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF171722))
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "MOODLOG",
            fontSize = 27.sp,
            color = Color(0xFFB69CFF)
        )

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "Привет! 👋",
            fontSize = 28.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Как ты себя чувствуешь?",
            fontSize = 17.sp,
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = today,
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Тип записи",
            fontSize = 18.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            FilterChip(
                selected = isDayMode,
                onClick = {
                    isDayMode = true
                    saved = false
                },
                label = {
                    Text("За день")
                }
            )

            FilterChip(
                selected = !isDayMode,
                onClick = {
                    isDayMode = false
                    saved = false
                },
                label = {
                    Text("Сейчас")
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = if (isDayMode)
                "Общая оценка твоего дня"
            else
                "Как ты чувствуешь себя прямо сейчас?",
            fontSize = 14.sp,
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(18.dp))

        MoodSelector(
            selectedMood = selectedMood,
            onMoodSelected = {
                selectedMood = it
                saved = false
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = if (isDayMode)
                "Как прошёл твой день?"
            else
                "Что происходит сейчас?",
            fontSize = 18.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = note,
            onValueChange = {
                note = it
                saved = false
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            placeholder = {
                Text(
                    "Запиши свои мысли...",
                    color = Color.Gray
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFFB69CFF),
                unfocusedBorderColor = Color(0xFF454555),
                cursorColor = Color(0xFFB69CFF)
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {
                if (selectedMood != 0) {
                    if (isDayMode) {
                        onSaveDayMood(selectedMood, note)
                    } else {
                        onSaveMomentMood(selectedMood, note)
                    }

                    saved = true
                    selectedMood = 0
                    note = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF9270E8)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = if (isDayMode)
                    "Сохранить оценку дня"
                else
                    "Зафиксировать настроение",
                fontSize = 16.sp
            )
        }

        if (saved) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "✓ Запись сохранена!",
                color = Color(0xFF81C784),
                fontSize = 14.sp
            )
        }
    }
}
