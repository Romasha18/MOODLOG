
package com.artur.moodlog.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.artur.moodlog.data.repository.MoodRepository
import com.artur.moodlog.ui.components.BottomNavigationBar
import com.artur.moodlog.ui.screens.HomeScreen
import com.artur.moodlog.ui.screens.HistoryScreen
import com.artur.moodlog.ui.screens.ProfileScreen
import com.artur.moodlog.ui.screens.StatisticsScreen
import com.artur.moodlog.viewmodel.MoodViewModel
import com.artur.moodlog.viewmodel.MoodViewModelFactory

@Composable
fun AppNavigation(repository: MoodRepository) {

    val navController = rememberNavController()

    val viewModel: MoodViewModel = viewModel(
        factory = MoodViewModelFactory(repository)
    )

    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route ?: "today"

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedScreen = currentRoute,
                onScreenSelected = { route ->
                    navController.navigate(route) {
                        popUpTo("today") {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "today",
            modifier = Modifier.padding(innerPadding)
        ) {

            composable("today") {
                HomeScreen(
                    onSaveDayMood = { mood, note ->
                        viewModel.saveDayMood(mood, note)
                    },
                    onSaveMomentMood = { mood, note ->
                        viewModel.saveMomentMood(mood, note)
                    }
                )
            }

            composable("history") {
                HistoryScreen(viewModel)
            }

            composable("statistics") {
                StatisticsScreen(viewModel)
            }

            composable("profile") {
                ProfileScreen()
            }
        }
    }
}
