package com.apex.apextest.views.splash


/**
 * UI State that represents SplashScreen
 **/
sealed class SplashState {
    object Loading : SplashState()
    object InvalidToken : SplashState()
    object Success : SplashState()
}

/**
 * Splash Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class SplashActions(
    val onAnimationCompleted: () -> Unit = {},
)