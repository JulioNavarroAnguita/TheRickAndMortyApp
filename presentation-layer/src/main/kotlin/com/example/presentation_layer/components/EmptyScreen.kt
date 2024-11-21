package com.example.presentation_layer.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.R


@Composable
fun EmptyScreen(@DrawableRes image: Int, @StringRes message: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "Empty State",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = message),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmptyScreenPreview() {
    Column(
        modifier = Modifier.background(Color.White)
    ) {
        EmptyScreen(image = R.drawable.empty_screen, message = R.string.empty_list)
    }
}