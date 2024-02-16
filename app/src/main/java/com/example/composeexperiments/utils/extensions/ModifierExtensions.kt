package com.example.composeexperiments.utils.extensions

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier

fun Modifier.maxWidthMinHeight(): Modifier =
    this.fillMaxWidth().height(IntrinsicSize.Min)