package com.example.composeexperiments.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeexperiments.ui.navigation.RootScreenModel

@Composable
fun ScreenTopBar(title: String, tintColor: Color, onDrawerToggle: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        IconButton(onClick = onDrawerToggle) {
            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu", tint = tintColor)
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.displayMedium,
            color = tintColor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
@Preview
fun ScreenTopBar_Preview() {
    ScreenTopBar(
        title = RootScreenModel.DemoList.label,
        tintColor = Color.Black
    ) {}
}