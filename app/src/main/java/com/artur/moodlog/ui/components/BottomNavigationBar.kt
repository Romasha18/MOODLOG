package com.artur.moodlog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavigationBar(
    selectedScreen: String,
    onScreenSelected: (String) -> Unit
) {
    val items = listOf(
        "today" to "Сегодня",
        "history" to "История",
        "statistics" to "Статистика",
        "profile" to "Профиль"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF222230))
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { (route, title) ->
            Text(
                text = title,
                color = if (selectedScreen == route) {
                    Color(0xFFB69CFF)
                } else {
                    Color.Gray
                },
                fontSize = 12.sp,
                modifier = Modifier.clickable {
                    onScreenSelected(route)
                }
            )
        }
    }
}