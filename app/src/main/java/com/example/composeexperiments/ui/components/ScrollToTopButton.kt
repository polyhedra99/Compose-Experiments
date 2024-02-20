package com.example.composeexperiments.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ScrollToTopButton() {
    Box(modifier = Modifier
        .clip(RoundedCornerShape(16.dp))
        .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
        .background(Color.DarkGray)
        .padding(24.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = "To top",
            tint = Color.White,
            modifier = Modifier.scale(2f)
        )
    }
}

@Composable
@Preview
fun ScrolToTopButton_Preview() {
    ScrollToTopButton()
}