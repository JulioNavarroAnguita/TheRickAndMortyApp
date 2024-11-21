package com.example.presentation_layer.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    showBackButton: Boolean,
    darkTheme: Boolean,
    title: AnnotatedString,
    onBackPressed: () -> Unit,
    onDarkModeClicked: () -> Unit
) {

    Column {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    style = MaterialTheme.typography.displayLarge
                )
            },
            navigationIcon = {
                if (showBackButton) {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            },
            actions = {
                val iconRotation by animateFloatAsState(
                    targetValue = if (darkTheme) 180f else 0f,
                    label = ""
                )
                IconButton(onClick = {
                    onDarkModeClicked()
                }) {
                    val icon =
                        if (darkTheme) Icons.Default.WbSunny else Icons.Default.NightlightRound
                    Icon(
                        modifier = Modifier.graphicsLayer(rotationZ = iconRotation),
                        imageVector = icon, contentDescription = "Dark Mode"
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp)
                .height(1.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppBar() {
    AppBar(
        showBackButton = true,
        title = AnnotatedString("Characters"),
        onBackPressed = {},
        onDarkModeClicked = {},
        darkTheme = true
    )
}