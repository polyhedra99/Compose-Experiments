package com.example.composeexperiments

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composeexperiments.ui.components.shaders.WaveShader
import com.example.composeexperiments.ui.navigation.RootScreenModel
import com.example.composeexperiments.ui.navigation.DrawerContents
import com.example.composeexperiments.ui.navigation.NavigationComponent
import com.example.composeexperiments.utils.constants.UiConstants
import kotlinx.coroutines.launch

@Composable
fun MainContent() {
    val navController = rememberNavController()

    WaveShader(
        color = Color.LightGray,
        modifier = Modifier
            .fillMaxSize()
            .scale(scaleX = 1f, scaleY = -1f)
    )

    RootDrawer(navController = navController)
    RootContent(navController = navController)
}

// ---- Internals ----
@Composable
fun RootDrawer(navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val selectedScreen = RootScreenModel.entries.firstOrNull {
        it.route == currentDestination?.route
    } ?: UiConstants.DEFAULT_ROOT_SCREEN

    DrawerContents(
        selectedScreen = selectedScreen,
        onScreenSelected = { screen -> screenSelected(navController, screen) }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RootContent(
    navController: NavHostController
) {
    val drawerDraggableState = remember {
        constructDrawerDraggableState()
    }
    val fraction by remember {
        derivedStateOf {
            drawerDraggableState.offset / UiConstants.DRAWER_WIDTH
        }
    }

    val scope = rememberCoroutineScope()
    val toggleDrawerState = {
        scope.launch {
            if (drawerDraggableState.currentValue == DrawerValue.Open) {
                drawerDraggableState.animateTo(DrawerValue.Closed)
            } else {
                drawerDraggableState.animateTo(DrawerValue.Open)
            }
        }
    }
    NavigationComponent(
        navController = navController,
        onDrawerToggle = { toggleDrawerState() },
        modifier = Modifier
            .graphicsLayer {
                this.translationX = drawerDraggableState.offset
                val scale = lerp(1f, 0.8f, fraction)
                this.scaleX = scale
                this.scaleY = scale
            }
            .anchoredDraggable(drawerDraggableState, Orientation.Horizontal),
        childModifier = Modifier
            .graphicsLayer {
                val roundedCorners = lerp(0f, 32.dp.toPx(), fraction)
                this.shape = RoundedCornerShape(roundedCorners)
                this.clip = true
            },
        isDrawerOpened = drawerDraggableState.currentValue == DrawerValue.Open
    )
}

@OptIn(ExperimentalFoundationApi::class)
private fun constructDrawerDraggableState() = AnchoredDraggableState(
    initialValue = DrawerValue.Closed,
    anchors = DraggableAnchors {
        DrawerValue.Closed at 0f
        DrawerValue.Open at UiConstants.DRAWER_WIDTH.dp.value
    },
    positionalThreshold = { distance: Float -> distance * 0.5f },
    velocityThreshold = { UiConstants.DRAWER_VELOCITY_THRESHOLD },
    animationSpec = tween()
)

private fun screenSelected(navController: NavController, screen: RootScreenModel) {
    navController.navigate(screen.route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

