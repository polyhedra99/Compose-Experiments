package com.example.composeexperiments.utils.constants

object WorkChatConstants {
    const val DEFAULT_ACTION_SIZE = 90
    const val VELOCITY_THRESHOLD = 140
    const val ITEM_MIN_BOTTOM_PADDING = 2f
    const val ITEM_MAX_BOTTOM_PADDING = 6f
    const val ACTION_SCALE = 1.3f
    const val ACTION_ALPHA = 0.4f
    const val COLOR_ANIMATION_DURATION = 350
    const val PADDING_ANIMATION_DURATION = 150

    val LOCAL_TIME: (String) -> String = { "Local time $it" }
    val COWORKER_NAME: (Int) -> String  = { "Co-worker $it" }
}