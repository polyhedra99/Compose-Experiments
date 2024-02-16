package com.example.composeexperiments.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeexperiments.R
import com.example.composeexperiments.model.WorkListItemModel
import com.example.composeexperiments.ui.components.shaders.BleakShader
import com.example.composeexperiments.ui.components.shaders.WaveShader
import com.example.composeexperiments.utils.constants.UiConstants
import com.example.composeexperiments.utils.constants.WorkChatConstants
import com.example.composeexperiments.utils.extensions.maxWidthMinHeight

@Composable
fun WorkListItem(
    model: WorkListItemModel,
    modifier: Modifier = Modifier
) {
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
                    modifier = Modifier.fillMaxSize()
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

@Composable
@Preview
fun WorkListItem_Preview() {
    WorkListItem(WorkListItemModel.defaultModel())
}
