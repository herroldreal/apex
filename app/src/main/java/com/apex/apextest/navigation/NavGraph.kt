package com.apex.apextest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.apex.apextest.views.main.MainRoute
import com.apex.apextest.views.splash.SplashRoute

object NavGraph {
    @Composable
    fun Setup(navController: NavHostController, isExpandedScreen: Boolean) {
        NavHost(
            navController = navController,
            startDestination = SplashDirections.Root.destination,
        ) {
            navigation(
                startDestination = SplashDirections.Splash.destination,
                route = SplashDirections.Root.destination,
            ) {
                composable(SplashDirections.Splash.destination) {
                    SplashRoute()
                }
                composable(SplashDirections.Main.destination) {
                    MainRoute()
                }
            }
        }
    }
}