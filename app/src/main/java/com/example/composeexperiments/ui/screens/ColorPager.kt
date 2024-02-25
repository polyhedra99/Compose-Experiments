package com.example.composeexperiments.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeexperiments.ui.components.ScreenTopBar
import com.example.composeexperiments.ui.navigation.RootScreenModel
import com.example.composeexperiments.utils.constants.ColorPagerConstants
import com.example.composeexperiments.utils.constants.UiConstants

@Composable
fun ColorPager(
    modifier: Modifier,
    onDrawerToggle: () -> Unit
) {
    Card(
        shape = RectangleShape,
        modifier = modifier
    ) {
        var redValue = rememberSaveable {
            mutableFloatStateOf(0.7f)
        }
        var greenValue = rememberSaveable {
            mutableFloatStateOf(0.1f)
        }
        var blueValue = rememberSaveable {
            mutableFloatStateOf(0.2f)
        }
        Column {
            ScreenTopBar(
                title = RootScreenModel.DemoPager.label,
                tintColor = Color.Black,
                onDrawerToggle = onDrawerToggle
            )

            ColorPager(redValue.floatValue, greenValue.floatValue, blueValue.floatValue)
            Spacer(modifier = Modifier.height(32.dp))
            PagerTitle()
            PagerBody()
            ColorSliders(listOf(redValue, greenValue, blueValue))
        }
    }
}

@Composable
private fun ColumnScope.PagerTitle() {
    Text(
        text = ColorPagerConstants.PAGER_TITLE,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.align(Alignment.CenterHorizontally)
    )
}

@Composable
private fun ColumnScope.PagerBody() {
    Text(
        text = UiConstants.LOREM_IPSUM,
        style = MaterialTheme.typography.bodyLarge,
        fontStyle = FontStyle.Italic,
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(8.dp)
            .padding(horizontal = 24.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ColorPager(
    redValue: Float,
    greenValue: Float,
    blueValue: Float
) {
    val pagerState = rememberPagerState(pageCount = {
        UiConstants.PAGER_PAGE_COUNT
    })
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 48.dp)
    ) { page ->
        val bgColor = Color(
            red = calculateNextValue(redValue, page),
            green = calculateNextValue(greenValue, page),
            blue = calculateNextValue(blueValue, page)
        )
        val inverseColor = calculateInverse(bgColor)

        PageData(bgColor = bgColor, inverseColor = inverseColor)
    }
}

@Composable
private fun PageData(
    bgColor: Color,
    inverseColor: Color
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor.copy(alpha = ColorPagerConstants.PAGE_BG_ALPHA)),
        contentAlignment = Alignment.Center
    ) {
        Text(UiConstants.LOREM_IPSUM, color = inverseColor,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(36.dp).padding(bottom = 48.dp))
        Box(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(bgColor)
                .align(Alignment.BottomCenter)
                .padding(8.dp)
        ) {
            Text(ColorPagerConstants.PAGE_CARD_BODY, color = inverseColor,
                style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun ColorSliders(colorStates: List<MutableState<Float>>) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(
                red = colorStates[0].value, green = colorStates[1].value,
                blue = colorStates[2].value, alpha = 0.2f
            ),
        ),
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        colorStates.forEachIndexed { index, state ->
            Text(
                text = when (index) {
                    0 -> ColorPagerConstants.RED_SLIDER_TITLE
                    1 -> ColorPagerConstants.GREEN_SLIDER_TITLE
                    else -> ColorPagerConstants.BLUE_SLIDER_TITLE
                },
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Slider(
                value = state.value,
                onValueChange = { state.value = it },
                modifier = Modifier.padding(horizontal = 16.dp),
                colors = when (index) {
                    0 -> SliderDefaults.colors(
                        thumbColor = Color.Black.copy(red = state.value /
                                ColorPagerConstants.SLIDER_HEAD_COLOR_FRACTION),
                    )
                    1 -> SliderDefaults.colors(
                        thumbColor = Color.Black.copy(green = state.value /
                                ColorPagerConstants.SLIDER_HEAD_COLOR_FRACTION),
                    )
                    else -> SliderDefaults.colors(
                        thumbColor = Color.Black.copy(blue = state.value /
                                ColorPagerConstants.SLIDER_HEAD_COLOR_FRACTION),
                    )
                }
            )
        }
    }
}

private fun calculateNextValue(colorChannelValue: Float, page: Int) =
    colorChannelValue + ((0f - colorChannelValue) / UiConstants.PAGER_PAGE_COUNT) * page

private fun calculateInverse(color: Color) = Color(
    red = 1.0f - color.red,
    green = 1.0f - color.green,
    blue = 1.0f - color.blue
)

@Composable
@Preview
fun DemoPager_Preview() {
    ColorPager(Modifier) {}
}