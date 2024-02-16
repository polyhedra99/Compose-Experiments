package com.example.composeexperiments.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeexperiments.model.WorkListItemModel
import com.example.composeexperiments.ui.components.ScreenTopBar
import com.example.composeexperiments.ui.components.WorkListItem
import com.example.composeexperiments.ui.navigation.RootScreenModel
import com.example.composeexperiments.ui.theme.PurpleGrey40
import com.example.composeexperiments.utils.constants.UiConstants
import com.example.composeexperiments.utils.constants.WorkChatConstants

@Composable
fun WorkList(
    modifier: Modifier,
    onDrawerToggle: () -> Unit
) {
    Card(
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = PurpleGrey40
        ),
        modifier = modifier
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End
        ) {
            item {
                ScreenTopBar(
                    title = RootScreenModel.WorkList.label,
                    tintColor = Color.White,
                    onDrawerToggle = onDrawerToggle
                )
            }

            val iterations = 75
            val generatedList = generateWorkListItems(iterations)
            val step = (Color.Black.red - Color.DarkGray.red) / iterations.toFloat()
            items(count = iterations, key = { generatedList.get(it).index }) { index ->
                WorkListItem(
                    model = generatedList.get(index),
                    modifier = Modifier
                        .padding(bottom = 6.dp)
                        .background(generateColorFor(index, step))
                )
            }
        }
    }
}

@Composable
@Preview
fun WorkList_Preview() {
    WorkList(Modifier) {}
}

private fun generateWorkListItems(iterations: Int): List<WorkListItemModel> {
    return (1..iterations).map { iteration ->
        WorkListItemModel(
            index = iteration,
            title = WorkChatConstants.COWORKER_NAME(iteration),
            body = UiConstants.LOREM_IPSUM
        )
    }
}

private fun generateColorFor(index: Int, step: Float): Color =
    Color(
        alpha = 0.8f,
        red = Color.DarkGray.red + index * step,
        green = Color.DarkGray.green + index * step,
        blue = Color.DarkGray.blue + index * step
    )
