package com.example.composeexperiments.ui.navigation

import androidx.compose.ui.graphics.Color

enum class DrawerColorCombination(
    val labelColor: Color,
    val primaryColor: Color,
    val secondaryColor: Color
) {
    BlueRed(
        labelColor = Color.White,
        primaryColor = Color.Blue,
        secondaryColor = Color.Red
    ),
    CyanGreen(
        labelColor = Color.Black,
        primaryColor = Color.Cyan,
        secondaryColor = Color.Green
    ),
    RedYellow(
        labelColor = Color.Black,
        primaryColor = Color.Red,
        secondaryColor = Color.Yellow
    ),
    NotSelected(
        labelColor = Color.Black,
        primaryColor = Color.LightGray,
        secondaryColor = Color.Gray
    )
}