package com.apex.apextest.views.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun MainRoute(
    coordinator: MainCoordinator = rememberMainCoordinator(),
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsState(MainState.Loading)

    // UI Actions
    val actions = rememberMainActions(coordinator)

    // UI Rendering
    MainScreen(uiState, actions)
}


@Composable
fun rememberMainActions(coordinator: MainCoordinator): MainActions {
    return remember(coordinator) {
        MainActions(
            onClick = coordinator::onCharacterClicked,
            toCharacterDetail = coordinator::onCharacterClicked
        )
    }
}