package com.artur.moodlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.room.Room
import com.artur.moodlog.data.local.MoodDatabase
import com.artur.moodlog.data.repository.MoodRepository
import com.artur.moodlog.navigation.AppNavigation
import com.artur.moodlog.ui.theme.MOODLOGTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = MoodDatabase.getDatabase(applicationContext)
        val repository = MoodRepository(database.moodDao())

        setContent {
            MOODLOGTheme {
                Surface(
                    color = Color(0xFF171722)
                ) {
                    AppNavigation(repository)
                }
            }
        }
    }
}