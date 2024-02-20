package com.example.composeexperiments.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeexperiments.model.WorkListInteractions
import com.example.composeexperiments.model.WorkListItemModel
import com.example.composeexperiments.ui.components.ScreenTopBar
import com.example.composeexperiments.ui.components.ScrollToTopButton
import com.example.composeexperiments.ui.components.WorkListItem
import com.example.composeexperiments.ui.navigation.RootScreenModel
import com.example.composeexperiments.ui.theme.PurpleGrey40
import com.example.composeexperiments.utils.constants.UiConstants
import com.example.composeexperiments.utils.constants.WorkChatConstants
import kotlinx.coroutines.launch

@Composable
fun WorkList(
    modifier: Modifier,
    interactions: WorkListInteractions,
    onDrawerToggle: () -> Unit
) {
    Box(
        modifier = modifier.background(PurpleGrey40)
    ) {
        val listState = rememberLazyListState()
        val scope = rememberCoroutineScope()

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            state = listState,
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
            items(count = iterations, key = { generatedList.get(it).index }) { index ->
                WorkListItem(
                    model = generatedList.get(index),
                    interactions = interactions,
                    color = Color.DarkGray,
                    modifier = Modifier
                        .padding(bottom = WorkChatConstants.ITEM_BOTTOM_PADDING.dp)
                )
            }
        }
        AnimatedVisibility(
            visible = listState.canScrollBackward,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .clickable {
                    scope.launch {
                        listState.animateScrollToItem(0)
                    }
                }
        ) {
            ScrollToTopButton()
        }
    }
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

@Composable
@Preview
fun WorkList_Preview() {
    WorkList(
        modifier = Modifier,
        interactions = WorkListInteractions.defaultInteractions(),
        onDrawerToggle = {}
    )
}
