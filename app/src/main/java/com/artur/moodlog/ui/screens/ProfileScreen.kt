package com.artur.moodlog.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF171722))
            .padding(24.dp)
    ) {
        Text(
            text = "Профиль",
            fontSize = 28.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF252536)
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "👤",
                    fontSize = 40.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Пользователь MOODLOG",
                    color = Color.White,
                    fontSize = 19.sp
                )

                Text(
                    text = "Твой личный дневник настроения",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "О приложении",
            color = Color(0xFFB69CFF),
            fontSize = 17.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "MOODLOG — пространство, где можно отслеживать своё эмоциональное состояние, сохранять мысли и наблюдать за изменениями.",
            color = Color.LightGray,
            fontSize = 14.sp,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Версия приложения: 2.0.0",
            color = Color.Gray,
            fontSize = 13.sp
        )
    }
}