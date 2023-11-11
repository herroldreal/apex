package com.apex.apextest.views.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.apex.apextest.navigation.NavigationManager
import com.apex.apextest.navigation.SplashDirections
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class SplashCoordinator(
    val viewModel: SplashViewModel,
    val navigatorManager: NavigationManager,
) {
    val screenStateFlow = viewModel.stateFlow

    fun loadingCompleted() {
        navigateToMain()
    }

    private fun navigateToMain() {
        navigatorManager.navigate(SplashDirections.Main)
    }
}

@Composable
fun rememberSplashCoordinator(
    viewModel: SplashViewModel = koinViewModel(),
    navigatorManager: NavigationManager = koinInject<NavigationManager>(),
): SplashCoordinator {
    return remember(viewModel) {
        SplashCoordinator(
            viewModel = viewModel,
            navigatorManager = navigatorManager,
        )
    }
}