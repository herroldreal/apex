package com.apex.apextest.navigation

import androidx.navigation.NamedNavArgument

sealed class SplashDirections {
    object Root : NavigationCommand {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val destination: String = "splash"
    }

    object Splash : NavigationCommand {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val destination: String = "splash_screen"
    }

    object Main : NavigationCommand {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val destination: String = "dashboard"
    }

    object Default : NavigationCommand {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val destination: String = ""
    }
}