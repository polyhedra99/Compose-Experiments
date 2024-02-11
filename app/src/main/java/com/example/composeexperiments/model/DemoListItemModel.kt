package com.example.composeexperiments.model

import androidx.compose.ui.graphics.Color
import com.example.composeexperiments.utils.constants.UiConstants

data class DemoListItemModel(
    val index: Int,
    val title: String,
    val body: String,
    val primaryColor: Color,
    val secondaryColor: Color
) {
    // I honestly prefer such companion to default values, readability-wise
    companion object {
        fun defaultModel() = DemoListItemModel(
            index = 1,
            title = "Title",
            body = UiConstants.LOREM_IPSUM,
            primaryColor = Color.Gray,
            secondaryColor = Color.DarkGray
        )
    }
}