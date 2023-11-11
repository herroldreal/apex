package com.apex.apextest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.view.WindowCompat
import com.apex.apextest.navigation.NavigationManager
import com.apex.apextest.views.ApexApp
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navigationManager: NavigationManager = get()
            val widthSizeClass = calculateWindowSizeClass(activity = this).widthSizeClass
            ApexApp(navigationManager, widthSizeClass)
        }
    }
}