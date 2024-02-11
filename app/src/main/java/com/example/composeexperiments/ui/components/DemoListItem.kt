package com.example.composeexperiments.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.composeexperiments.model.DemoListItemModel
import com.example.composeexperiments.utils.constants.UiConstants

@Composable
fun DemoListItem(model: DemoListItemModel, modifier: Modifier = Modifier) {
    val thumbnailWidth = remember { mutableIntStateOf(IntSize.Zero.width) }
    val extended = rememberSaveable { mutableStateOf(false) }

    DemoListItemContainer(modifier = modifier, extended = extended) {
        DemoListItemStaticPart(model, thumbnailWidth)
        DemoListItemExpandablePart(model, extended, thumbnailWidth)
    }
}

// ---- Internals ----
@Composable
private fun DemoListItemContainer(
    modifier: Modifier,
    extended: MutableState<Boolean>,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .padding(bottom = UiConstants.DEMO_LIST_CROSS_ITEM_PADDING.dp)
            .fillMaxWidth()
            .shadow(
                elevation = UiConstants.DEMO_LIST_ITEM_ELEVATION.dp,
                shape = RoundedCornerShape(topStart = UiConstants.DEMO_LIST_OUTLINE_RADIUS.dp)
            )
            .clip(shape = RoundedCornerShape(topStart = UiConstants.DEMO_LIST_OUTLINE_RADIUS.dp))
            .clickable { extended.value = !extended.value },
        content = content
    )
}

@Composable
private fun DemoListItemStaticPart(
    model: DemoListItemModel,
    thumbnailWidth: MutableState<Int>
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .background(model.primaryColor)
                .aspectRatio(0.5f)
                .onSizeChanged {
                    thumbnailWidth.value = it.width
                }
        )
        DemoListItemDot(color = model.secondaryColor)

        DemoListItemText(
            title = "${model.title} ${model.index}",
            body = model.body
        )
    }
}

@Composable
private fun DemoListItemExpandablePart(
    model: DemoListItemModel,
    extended: MutableState<Boolean>,
    thumbnailWidth: MutableState<Int>
) {
    AnimatedVisibility(visible = extended.value, enter = expandVertically(), exit = shrinkVertically()) {
        Row(
            Modifier
                .background(Color.DarkGray)
                .height(IntrinsicSize.Min)
        ) {
            Box(
                modifier = Modifier
                    .width(with(LocalDensity.current) {
                        thumbnailWidth.value.toDp()
                    })
                    .background(model.primaryColor)
                    .fillMaxHeight()
            )
            Text(text = model.body, color = Color.White,
                modifier = Modifier
                    .padding(8.dp)
                    .padding(start = 32.dp))
        }
    }
}

@Composable
private fun RowScope.DemoListItemDot(color: Color) {
    Box(
        modifier = Modifier
            .offset(x = -UiConstants.DEMO_LIST_OUTLINE_RADIUS.dp)
            .align(Alignment.CenterVertically)
    ) {
        Box(
            modifier = Modifier
                .background(Color.White, shape = CircleShape)
                .width(UiConstants.DEMO_LIST_OUTLINE_RADIUS.dp * 2)
                .align(Alignment.Center)
                .aspectRatio(1f)
        )
        Box(
            modifier = Modifier
                .background(color, shape = CircleShape)
                .width(UiConstants.DEMO_LIST_SECONDARY_RADIUS.dp * 2)
                .align(Alignment.Center)
                .aspectRatio(1f)
        )
    }
}

@Composable
private fun DemoListItemText(
    title: String,
    body: String
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(text = body, minLines = 2, maxLines = 2,
            overflow = TextOverflow.Ellipsis)
    }
}

@Composable
@Preview
fun DemoListItem_Preview() {
    DemoListItem(model = DemoListItemModel.defaultModel())
}