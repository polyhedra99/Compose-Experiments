package com.example.composeexperiments.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlin.math.max
import kotlin.math.pow

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

        val visibleCount by remember {
            derivedStateOf { listState.layoutInfo.visibleItemsInfo.size }
        }
        val colorStep by remember(visibleCount) {
            derivedStateOf { calculateItemColorStep(visibleCount) }
        }
        val paddingStep by remember(visibleCount) {
            derivedStateOf { calculateItemPaddingStep(visibleCount) }
        }
        val firstVisibleIndex by remember {
            derivedStateOf { listState.firstVisibleItemIndex }
        }

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
                val animatedColor = animateColorAsState(
                    targetValue = calculateColorForStepDiff(
                        (index - firstVisibleIndex) * colorStep
                    ),
                    label = "Animated Color",
                    animationSpec = tween(WorkChatConstants.COLOR_ANIMATION_DURATION)
                )
                val animatedPadding = animateDpAsState(
                    targetValue = calculatePaddingForStepDiff(
                        paddingStep * (index - firstVisibleIndex)
                    ).dp,
                    label = "",
                    animationSpec = tween(WorkChatConstants.PADDING_ANIMATION_DURATION)
                )
                WorkListItem(
                    model = generatedList.get(index),
                    interactions = interactions,
                    color = animatedColor.value,
                    modifier = Modifier.padding(bottom = animatedPadding.value)
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

private fun calculateItemColorStep(totalVisible: Int): Float =
    if (totalVisible == 0) 0f else (Color.Black.red - Color.DarkGray.red) / totalVisible.toFloat()

private fun calculateItemPaddingStep(totalVisible: Int): Float =
    if (totalVisible == 0) 0f else (WorkChatConstants.ITEM_MAX_BOTTOM_PADDING -
            WorkChatConstants.ITEM_MIN_BOTTOM_PADDING) / totalVisible.toFloat()

private fun calculateColorForStepDiff(
    stepDiff: Float
): Color = Color.DarkGray.copy(
    red = max(Color.DarkGray.red + stepDiff, 0f),
    green = max(Color.DarkGray.green + stepDiff, 0f),
    blue = max(Color.DarkGray.blue + stepDiff, 0f)
)

private fun calculatePaddingForStepDiff(
    stepDiff: Float
): Float = (WorkChatConstants.ITEM_MAX_BOTTOM_PADDING - stepDiff +
        WorkChatConstants.ITEM_MIN_BOTTOM_PADDING).pow(3) / 32f

@Composable
@Preview
fun WorkList_Preview() {
    WorkList(
        modifier = Modifier,
        interactions = WorkListInteractions.defaultInteractions(),
        onDrawerToggle = {}
    )
}
