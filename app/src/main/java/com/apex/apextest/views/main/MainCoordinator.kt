package com.apex.apextest.views.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.androidx.compose.koinViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class MainCoordinator(
    val viewModel: MainViewModel,
) {
    val screenStateFlow = viewModel.stateFlow

    init {
        viewModel.fetchAllCharacters()
    }

    fun onCharacterClicked(characterId: Int?) {
        viewModel.showCharacterDetail(characterId)
    }
}

@Composable
fun rememberMainCoordinator(
    viewModel: MainViewModel = koinViewModel(),
): MainCoordinator {
    return remember(viewModel) {
        MainCoordinator(
            viewModel = viewModel,
        )
    }
}