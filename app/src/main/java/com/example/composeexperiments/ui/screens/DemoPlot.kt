package com.example.composeexperiments.ui.screens

import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.composeexperiments.ui.components.ScreenTopBar
import com.example.composeexperiments.ui.navigation.RootScreenModel
import com.example.composeexperiments.ui.theme.BlueGrey
import com.example.composeexperiments.utils.constants.UiConstants
import kotlin.math.cos
import kotlin.math.sin

typealias FunctionInfo = Pair<Color, (Int, Size) -> Offset>

@Composable
fun DemoPlot(
    modifier: Modifier,
    onDrawerToggle: () -> Unit
) {
    Card(
        shape = RectangleShape,
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = BlueGrey)
    ) {
        Box {
            Plot()

            Column(modifier = Modifier.fillMaxSize()) {
                ScreenTopBar(
                    title = RootScreenModel.DemoPlot.label,
                    tintColor = Color.White,
                    onDrawerToggle = onDrawerToggle
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "No specific purpose for these plots, " +
                        "gray lines do not represent specific values.",
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun Plot() {
    var angle by remember { mutableIntStateOf(0) }
    val divValue = 10
    LaunchedEffect(Unit) {
        while (true) {
            withInfiniteAnimationFrameMillis {
                angle = it.div(divValue).toInt()
            }
        }
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        canvasLines()

        val listOfFunctions = listOf<FunctionInfo>(
            Pair(Color.Red) { angle, size -> infinityOffsetFor(angle, size) },
            Pair(Color.Blue) { angle, size -> horizontalInfinityOffsetFor(angle, size) },
            Pair(Color.Black) { angle, size -> ellipseOffsetFor(angle, size) }
        )
        functionPlots(listOfFunctions)
        movingObjects(angle, listOfFunctions)
    }
}

@Composable
@Preview
fun DemoPlot_Preview() {
    DemoPlot(Modifier) {}
}

private fun DrawScope.canvasLines() {
    val linesColor = Color.LightGray.copy(alpha = 0.5f)
    val verticalFragmentsCount = 5
    for (i in 1..verticalFragmentsCount) {
        val xOffset = (size.width / verticalFragmentsCount.toFloat()) * i.toFloat()
        drawLine(
            color = linesColor,
            start = Offset(xOffset, 0f),
            end = Offset(xOffset, size.height),
            strokeWidth = UiConstants.PLOT_STROKE_WIDTH
        )
    }

    val horizontalFragmentsCount = 8
    for (i in 1..horizontalFragmentsCount) {
        val yOffset = (size.height / horizontalFragmentsCount.toFloat()) * i.toFloat()
        drawLine(
            color = linesColor,
            start = Offset(0f, yOffset),
            end = Offset(size.width, yOffset),
            strokeWidth = UiConstants.PLOT_STROKE_WIDTH
        )
    }
}

private fun DrawScope.functionPlots(functions: List<FunctionInfo>) {
    val pointsCollection = functions.map {
        List(361) { angle -> it.second(angle, size) }
    }
    pointsCollection.forEachIndexed { index, points ->
        drawPoints(points, PointMode.Polygon,
            color = functions.get(index).first.copy(alpha = 0.35f),
            strokeWidth = UiConstants.FUNCTIONS_STROKE_WIDTH
        )
    }
}

private fun DrawScope.movingObjects(angle: Int, functions: List<FunctionInfo>) {
    functions.forEachIndexed { index, pair ->
        drawCircle(
            color = pair.first,
            center = pair.second(angle, size),
            radius = lerp(
                UiConstants.FUNCTIONS_MIN_RADIUS,
                UiConstants.FUNCTIONS_MAX_RADIUS,
                1f / (index + 1)
            )
        )
    }
}

private fun infinityOffsetFor(angle: Int, size: Size): Offset {
    val t = Math.toRadians(angle.toDouble())
    return Offset(
        400f * sin(2 * t).toFloat() + size.width / 2,
        800f * sin(t).toFloat() + size.height / 2
    )
}

private fun horizontalInfinityOffsetFor(angle: Int, size: Size): Offset {
    val t = Math.toRadians(angle.toDouble())
    return Offset(
        400f * cos(t).toFloat() + size.width / 2,
        250f * cos(t).toFloat() * sin(t).toFloat() + size.height / 2
    )
}

private fun ellipseOffsetFor(angle: Int, size: Size): Offset {
    val t = Math.toRadians(angle.toDouble())
    val rotationAngle = Math.toRadians(60.0)
    return Offset(
        400f * cos(t - rotationAngle).toFloat() + size.width / 2,
        250f * sin(t).toFloat() + size.height / 4
    )
}
