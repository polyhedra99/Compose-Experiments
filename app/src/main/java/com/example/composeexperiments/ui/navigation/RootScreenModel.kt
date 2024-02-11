package com.example.composeexperiments.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.graphics.vector.ImageVector

enum class RootScreenModel(
    val label: String,
    val icon: ImageVector,
    val color: DrawerColorCombination,
    val route: String
) {
    DemoList(
        label = "Demo List",
        icon = Icons.AutoMirrored.Filled.List,
        color = DrawerColorCombination.BlueRed,
        route = "demo_list"
    ),
    DemoPager(
        label = "Demo Pager",
        icon = Icons.Filled.Edit,
        color = DrawerColorCombination.CyanGreen,
        route = "demo_pager"
    ),
    DemoPlot(
        label = "Demo Plot",
        icon = Icons.Filled.DateRange,
        color = DrawerColorCombination.RedYellow,
        route = "demo_plot"
    )
}