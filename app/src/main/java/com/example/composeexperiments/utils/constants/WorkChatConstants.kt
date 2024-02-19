package com.example.composeexperiments.utils.constants

object WorkChatConstants {
    const val DEFAULT_ACTION_SIZE = 100
    const val VELOCITY_THRESHOLD = 200
    const val ITEM_BOTTOM_PADDING = 12
    const val ACTION_SCALE = 1.3f

    val LOCAL_TIME: (String) -> String = { "Local time $it" }
    val COWORKER_NAME: (Int) -> String  = { "Co-worker $it" }
}