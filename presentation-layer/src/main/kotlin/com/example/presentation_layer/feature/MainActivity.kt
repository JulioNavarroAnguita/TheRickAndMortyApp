package com.example.presentation_layer.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.presentation_layer.navigation.RickAndMortyApp
import com.example.presentation_layer.ui.theme.TheRickAndMortyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            TheRickAndMortyAppTheme {
                RickAndMortyApp()
            }
        }
    }
}