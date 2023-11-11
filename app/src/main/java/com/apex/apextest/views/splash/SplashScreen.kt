package com.apex.apextest.views.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.apex.apextest.R

@Composable
fun SplashScreen(
    state: SplashState,
    actions: SplashActions,
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        elevation = 8.dp,
        modifier = Modifier.fillMaxSize(),
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.apex_splash))
        val logoAnimationState =
            animateLottieCompositionAsState(composition = composition)
        LottieAnimation(
            modifier = Modifier.fillMaxSize(),
            composition = composition,
            progress = { logoAnimationState.progress },
        )
        if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
            actions.onAnimationCompleted()
        }
    }
}

@Composable
@Preview(name = "Splash")
private fun SplashScreenPreview() {
    SplashScreen(
        state = SplashState.Loading,
        actions = SplashActions(),
    )
}
