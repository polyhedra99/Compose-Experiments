package com.example.composeexperiments.ui.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composeexperiments.model.WorkListInteractions
import com.example.composeexperiments.ui.screens.WorkList
import com.example.composeexperiments.ui.screens.DemoPlot
import com.example.composeexperiments.ui.screens.ColorPager

@Composable
fun NavigationComponent(
    navController: NavHostController,
    modifier: Modifier,
    childModifier: Modifier,
    isDrawerOpened: Boolean,
    onDrawerToggle: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = RootScreenModel.WorkList.route,
        enterTransition = { slideInHorizontally { fullWidth -> fullWidth } },
        exitTransition = { slideOutHorizontally { fullWidth -> fullWidth } },
        modifier = modifier
    ) {
        composable(RootScreenModel.WorkList.route) {
            WorkList(
                childModifier,
                WorkListInteractions(isDrawerOpened),
                onDrawerToggle
            )
        }
        composable(RootScreenModel.DemoPlot.route) { DemoPlot(childModifier, onDrawerToggle) }
        composable(RootScreenModel.DemoPager.route) { ColorPager(childModifier, onDrawerToggle) }
    }
}