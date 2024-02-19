package com.example.composeexperiments.model

data class WorkListInteractions(
    val disableItemDrag: Boolean
) {
    companion object {
        fun defaultInteractions() = WorkListInteractions(true)
    }
}