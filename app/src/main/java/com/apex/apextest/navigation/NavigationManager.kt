package com.apex.apextest.navigation

import com.apex.apextest.navigation.SplashDirections.Default
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationManager {
    private val _commands: MutableStateFlow<NavigationCommand> = MutableStateFlow(Default)
    val commands = _commands.asStateFlow()

    fun navigate(
        directions: NavigationCommand
    ) {
        _commands.value = directions
    }
}