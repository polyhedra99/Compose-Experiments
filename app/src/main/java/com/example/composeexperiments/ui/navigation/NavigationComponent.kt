package com.example.composeexperiments.ui.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composeexperiments.ui.screens.WorkList
import com.example.composeexperiments.ui.screens.DemoPlot
import com.example.composeexperiments.ui.screens.DemoPager

@Composable
fun NavigationComponent(
    navController: NavHostController,
    modifier: Modifier,
    childModifier: Modifier,
    onDrawerToggle: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = RootScreenModel.WorkList.route,
        enterTransition = { slideInHorizontally { fullWidth -> fullWidth } },
        exitTransition = { slideOutHorizontally { fullWidth -> fullWidth } },
        modifier = modifier
    ) {
        composable(RootScreenModel.WorkList.route) { WorkList(childModifier, onDrawerToggle) }
        composable(RootScreenModel.DemoPlot.route) { DemoPlot(childModifier, onDrawerToggle) }
        composable(RootScreenModel.DemoPager.route) { DemoPager(childModifier, onDrawerToggle) }
    }
}