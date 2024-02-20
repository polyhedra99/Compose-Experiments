package com.example.composeexperiments.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.composeexperiments.R
import com.example.composeexperiments.model.AnchorsDoublet
import com.example.composeexperiments.model.WorkListInteractions
import com.example.composeexperiments.model.WorkListItemModel
import com.example.composeexperiments.ui.components.shaders.BleakShader
import com.example.composeexperiments.utils.constants.UiConstants
import com.example.composeexperiments.utils.constants.WorkChatConstants
import com.example.composeexperiments.utils.extensions.maxWidthMinHeight
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WorkListItem(
    model: WorkListItemModel,
    interactions: WorkListInteractions,
    color: Color,
    modifier: Modifier
) {
    val localDensity = LocalDensity.current
    val state = remember { constructDraggableState(localDensity) }

    Box(modifier = modifier) {
        WorkListItemLayout(model, Modifier.workItemModifications(state, interactions, color))
    }
}

@Composable
private fun WorkListItemLayout(model: WorkListItemModel, modifier: Modifier) {
    Layout(
        contents = listOf<@Composable () -> Unit>(
            { WorkListItemUpperLayout(model = model, modifier = modifier) },
            {
                DeleteAction()
                CallAction()
            }
        )
    ) { (upperMeasurable, actionMeasurables), constraints ->
        require(upperMeasurable.size == 1) {
            "Should emit only one upper layout"
        }
        val upperPlaceable = upperMeasurable.first().measure(constraints)
        val actionPlaceables = actionMeasurables.map {
            it.measure(
                constraints.copy(
                    minHeight = upperPlaceable.height,
                    maxHeight = upperPlaceable.height
                )
            )
        }

        layout(upperPlaceable.width, upperPlaceable.height) {
            actionPlaceables.forEachIndexed { index, placeable ->
                placeable.place(
                    x = upperPlaceable.width -
                            (placeable.width * (actionPlaceables.size - index)), y = 0
                )
            }
            upperPlaceable.place(x = 0, y = 0)
        }
    }
}

@Composable
private fun WorkListItemUpperLayout(model: WorkListItemModel, modifier: Modifier) {
    Layout(
        modifier = modifier,
        contents = listOf<@Composable () -> Unit>(
            { ItemThumbnail() },
            {
                ItemText(
                    name = WorkChatConstants.COWORKER_NAME(model.index),
                    body = UiConstants.LOREM_IPSUM
                )
            },
            { ItemFooter(WorkChatConstants.LOCAL_TIME("7:27")) }
        )
    ) { (thumbnailMeasurable,
            textMeasurable, footerMeasurable), constraints ->
        require(
            thumbnailMeasurable.size == 1 &&
                    textMeasurable.size == 1 &&
                    footerMeasurable.size == 1
        ) {
            "Should only emit one composable per each"
        }

        val thumbnailPlaceable = thumbnailMeasurable.first().measure(constraints)
        val textPlaceable = textMeasurable.first().measure(
            constraints.copy(maxWidth = constraints.maxWidth - thumbnailPlaceable.width))
        val footerPlaceable = footerMeasurable.first().measure(constraints)

        layout(constraints.maxWidth, thumbnailPlaceable.height) {
            textPlaceable.place(x = thumbnailPlaceable.width, y = 0)
            footerPlaceable.place(x = 0,
                y = thumbnailPlaceable.height - footerPlaceable.height)
            thumbnailPlaceable.place(x = 0, y = 0)
        }
    }
}

@Composable
private fun DeleteAction() {
    Box(modifier = Modifier
        .width(WorkChatConstants.DEFAULT_ACTION_SIZE.dp)
        .background(Color.Red.copy(alpha = WorkChatConstants.ACTION_ALPHA))
        .clickable { /* TODO("Not implemented") */ }
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            tint = Color.White,
            contentDescription = "Delete",
            modifier = Modifier
                .align(Alignment.Center)
                .scale(WorkChatConstants.ACTION_SCALE)
        )
    }
}

@Composable
private fun CallAction() {
    Box(modifier = Modifier
        .width(WorkChatConstants.DEFAULT_ACTION_SIZE.dp)
        .background(Color.Green.copy(alpha = WorkChatConstants.ACTION_ALPHA))
        .clickable { /* TODO("Not implemented") */ }
    ) {
        Icon(
            imageVector = Icons.Filled.Phone,
            tint = Color.White,
            contentDescription = "Delete",
            modifier = Modifier
                .align(Alignment.Center)
                .scale(WorkChatConstants.ACTION_SCALE)
        )
    }
}

@Composable
private fun ItemThumbnail() {
    Layout(
        modifier = Modifier
            .wrapContentSize()
            .padding(8.dp)
            .clip(RoundedCornerShape(16f))
            .background(Color.Gray),
        contents = listOf<@Composable () -> Unit>(
            {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Avatar background"
                )
            },
            {
                BleakShader(
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.7f)
                )
            }
        )
    ) { (avatarMeasurable, bleakMeasurable), constaints ->
        require(
            avatarMeasurable.size == 1 &&
                    bleakMeasurable.size == 1
        ) {
            "Should only emit one composable per each"
        }

        val avatarPlaceable = avatarMeasurable.first().measure(constaints)
        val bleakPlaceable = bleakMeasurable.first().measure(
            constaints.copy(
                maxWidth = avatarPlaceable.width,
                maxHeight = avatarPlaceable.height
            )
        )

        layout(avatarPlaceable.width, avatarPlaceable.height) {
            avatarPlaceable.place(x = 0, y = 0)
            bleakPlaceable.place(x = 0, y = 0)
        }
    }
}

@Composable
private fun ItemText(
    name: String,
    body: String
) {
    Column {
        ItemName(name = name)
        ItemBody(body = body)
    }
}

@Composable
private fun ItemName(name: String) {
    Text(
        text = name,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleLarge,
        color = Color.White,
        modifier = Modifier.itemTextPadding()
    )
}

@Composable
private fun ItemBody(body: String) {
    Text(
        text = body,
        style = MaterialTheme.typography.labelMedium,
        color = Color.White,
        minLines = 3,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.itemTextPadding()
    )
}

@Composable
private fun ItemFooter(localTime: String) {
    Row(modifier = Modifier
        .maxWidthMinHeight()
        .background(Color.Black.copy(alpha = 0.5f))
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = localTime,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .padding(2.dp)
                .padding(end = 14.dp)
        )
    }
}

@Composable
private fun Modifier.itemTextPadding(): Modifier =
    this
        .padding(top = 8.dp)
        .padding(horizontal = 16.dp)

private fun Modifier.draglessModifier(xOffset: Int) = this
    .offset {
        IntOffset(
            x = xOffset,
            y = 0
        )
    }

@OptIn(ExperimentalFoundationApi::class)
private fun constructDraggableState(localDensity: Density): AnchoredDraggableState<AnchorsDoublet> {
    val endActionSizePx = with(localDensity) {
        (WorkChatConstants.DEFAULT_ACTION_SIZE.dp * 2).toPx()
    }
    return AnchoredDraggableState(
        initialValue = AnchorsDoublet.First,
        anchors = DraggableAnchors {
            AnchorsDoublet.First at 0f
            AnchorsDoublet.Second at endActionSizePx
        },
        positionalThreshold = { distance: Float -> distance * 0.5f },
        velocityThreshold = {
            with(localDensity) {
                WorkChatConstants.VELOCITY_THRESHOLD.dp.toPx()
            }
        },
        animationSpec = tween(),
    )
}

@OptIn(ExperimentalFoundationApi::class)
private fun Modifier.workItemModifications(
    state: AnchoredDraggableState<AnchorsDoublet>,
    interactions: WorkListInteractions,
    color: Color,
): Modifier = if (interactions.disableItemDrag) {
    this.draglessModifier(-state.requireOffset().roundToInt())
        .background(color)
} else {
    this.draglessModifier(-state.requireOffset().roundToInt())
        .background(color).anchoredDraggable(state,
            Orientation.Horizontal, reverseDirection = true)
}

@OptIn(ExperimentalFoundationApi::class)
private fun constructItemModifierOld(
    state: AnchoredDraggableState<AnchorsDoublet>,
    interactions: WorkListInteractions,
    itemHeight: MutableState<Dp>,
    color: Color,
    localDensity: Density,
): Modifier {
    var customModifier = Modifier
        .zIndex(1f)
        .draglessModifier(
            -state
                .requireOffset()
                .roundToInt()
        )
        .onGloballyPositioned {
            itemHeight.value = with(localDensity) {
                it.size.height
                    .toFloat()
                    .toDp()
            }
        }
        .background(color)
    if (!interactions.disableItemDrag) {
        customModifier = customModifier
            .anchoredDraggable(state, Orientation.Horizontal, reverseDirection = true)
    }
    return customModifier
}

@Composable
@Preview
fun WorkListItem_Preview() {
    WorkListItem(
        WorkListItemModel.defaultModel(),
        WorkListInteractions.defaultInteractions(),
        Color.DarkGray,
        Modifier.padding(bottom = WorkChatConstants.ITEM_BOTTOM_PADDING.dp)
    )
}
