package com.apex.apextest.views

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.apex.apextest.navigation.NavGraph
import com.apex.apextest.navigation.NavigationManager
import com.apex.apextest.ui.theme.ApexTestTheme
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun ApexApp(
    navigationManager: NavigationManager,
    widthSizeClass: WindowWidthSizeClass
) {
    KoinAndroidContext {
        ApexTestTheme {
            val systemUiController: SystemUiController = rememberSystemUiController()
            val navController: NavHostController = rememberNavController()

            val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded

            SideEffect {
                systemUiController.isStatusBarVisible = false
                systemUiController.isNavigationBarVisible = false
            }

            NavGraph.Setup(navController, isExpandedScreen)
            navigationManager.commands.collectAsState().value.also { command ->
                if (command.destination.isNotEmpty()) {
                    navController.navigate(command.destination)
                }
            }
        }
    }
}