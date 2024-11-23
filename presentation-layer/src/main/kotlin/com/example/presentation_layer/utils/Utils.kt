package com.example.presentation_layer.utils

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Modifier.debounce(
    debounceTime: Long = 1000L,
    onClick: () -> Unit
): Modifier {

    var isClickable by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    return this.clickable {
        if (isClickable) {
            isClickable = false
            onClick()
            coroutineScope.launch {
                delay(debounceTime)
                isClickable = true
            }
        }
    }
}