package com.example.composeexperiments.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeexperiments.ui.components.ScreenTopBar
import com.example.composeexperiments.ui.navigation.RootScreenModel
import com.example.composeexperiments.utils.constants.UiConstants

@Composable
fun DemoPager(
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
            ColorSliders(listOf(redValue, greenValue, blueValue))
        }
    }
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

        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(bgColor)
                .padding(36.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(UiConstants.LOREM_IPSUM, color = inverseColor)
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
        colorStates.forEachIndexed { index, state ->
            Slider(
                value = state.value,
                onValueChange = { state.value = it },
                modifier = Modifier.padding(horizontal = 16.dp),
                colors = when (index) {
                    0 -> SliderDefaults.colors(
                        thumbColor = Color.Black.copy(red = state.value),
                        activeTrackColor = Color.Red
                    )

                    1 -> SliderDefaults.colors(
                        thumbColor = Color.Black.copy(green = state.value),
                        activeTrackColor = Color.Green
                    )

                    else -> SliderDefaults.colors(
                        thumbColor = Color.Black.copy(blue = state.value),
                        activeTrackColor = Color.Blue
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
    DemoPager(Modifier) {}
}