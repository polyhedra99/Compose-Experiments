package com.example.composeexperiments.utils.constants

import com.example.composeexperiments.ui.navigation.RootScreenModel

object UiConstants {
    const val DRAWER_WIDTH = 600
    const val DRAWER_VELOCITY_THRESHOLD = 250f
    const val PRIMARY_COLOR_CHANGE_DURATION_MS = 1000
    const val SECONDARY_COLOR_CHANGE_DURATION_MS = 500

    const val START_PADDING_CHANGE_DURATION_MS = 500
    const val START_PADDING_NOT_SELECTED = 32
    const val VERTICAL_PADDING_CHANGE_DURATION_MS = 250
    const val VERTICAL_PADDING_SELECTED = 2
    const val ELEVATION_NOT_SELECTED = 2
    const val ELEVATION_SELECTED = 4

    const val DEMO_LIST_SECONDARY_RADIUS = 12
    const val DEMO_LIST_OUTLINE_RADIUS = 16
    const val DEMO_LIST_CROSS_ITEM_PADDING = 24
    const val DEMO_LIST_ITEM_ELEVATION = 4
    const val DEMO_LIST_ITEM_ODD_PADDING = 48
    const val DEMO_LIST_ITEM_EVEN_PADDING = 86
    const val DEMO_LIST_ITEM_ODD_SCALE = 1.1f
    const val DEMO_LIST_ITEM_EVEN_SCALE = 1f

    const val FUNCTIONS_STROKE_WIDTH = 5f
    const val PLOT_STROKE_WIDTH = 0.5f
    const val FUNCTIONS_MIN_RADIUS = 40f
    const val FUNCTIONS_MAX_RADIUS = 100f

    const val PAGER_PAGE_COUNT = 5

    const val LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed " +
            "do eiusmod tempor incididunt ut labore et dolore magna aliqua."

    val DEFAULT_ROOT_SCREEN = RootScreenModel.DemoList
}