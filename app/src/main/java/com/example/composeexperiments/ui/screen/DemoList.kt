package com.example.composeexperiments.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeexperiments.model.DemoListItemModel
import com.example.composeexperiments.ui.components.DemoListItem
import com.example.composeexperiments.ui.components.ScreenTopBar
import com.example.composeexperiments.ui.navigation.RootScreenModel
import com.example.composeexperiments.utils.constants.UiConstants

@Composable
fun DemoList(
    modifier: Modifier,
    onDrawerToggle: () -> Unit
) {
    Card(
        shape = RectangleShape,
        modifier = modifier
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End
        ) {
            item {
                ScreenTopBar(
                    title = RootScreenModel.DemoList.label,
                    tintColor = Color.Black,
                    onDrawerToggle = onDrawerToggle
                )
            }

            val iterations = 25
            val generatedList = generateDemoListItems(iterations)
            items(count = iterations, key = { generatedList.get(it).index }) { index ->
                DemoListItem(
                    model = generatedList.get(index),
                    modifier = Modifier
                        .padding(start = decideOnStartPadding(index))
                        .scale(decideOnItemScale(index))
                )
            }
        }
    }
}

@Composable
@Preview
fun DemoList_Preview() {
    DemoList(Modifier) {}
}

private fun decideOnStartPadding(index: Int) =
    if (index.mod(2) == 0) {
        UiConstants.DEMO_LIST_ITEM_EVEN_PADDING.dp
    } else {
        UiConstants.DEMO_LIST_ITEM_ODD_PADDING.dp
    }

private fun decideOnItemScale(index: Int) =
    if (index.mod(2) == 0) {
        UiConstants.DEMO_LIST_ITEM_EVEN_SCALE
    } else {
        UiConstants.DEMO_LIST_ITEM_ODD_SCALE
    }

private fun generateDemoListItems(iterations: Int): List<DemoListItemModel> {
    val step = (Color.DarkGray.red - Color.LightGray.red) / iterations.toFloat()
    var primaryColor: Color
    var secondaryColor: Color
    return (0..iterations).map { iteration ->
        primaryColor = Color.LightGray.copy(
            red = Color.LightGray.red + step * iteration,
            green = Color.LightGray.green + step * iteration,
            blue = Color.LightGray.blue + step * iteration
        )
        secondaryColor = Color.DarkGray.copy(
            red = Color.DarkGray.red - step * iteration,
            green = Color.DarkGray.green - step * iteration,
            blue = Color.DarkGray.blue - step * iteration
        )
        DemoListItemModel(
            index = iteration,
            primaryColor = primaryColor,
            secondaryColor = secondaryColor,
            title = "Title",
            body = "Lorem Ipsum. Primary color: $primaryColor"
        )
    }
}
