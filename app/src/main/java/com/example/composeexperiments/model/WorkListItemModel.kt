package com.example.composeexperiments.model

import com.example.composeexperiments.utils.constants.UiConstants

data class WorkListItemModel(
    val index: Int,
    val title: String,
    val body: String
) {
    // I honestly prefer such companion to default values, readability-wise
    companion object {
        fun defaultModel() = WorkListItemModel(
            index = 1,
            title = "Title",
            body = UiConstants.LOREM_IPSUM
        )
    }
}