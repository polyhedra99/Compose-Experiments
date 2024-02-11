package com.example.composeexperiments.ui.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeexperiments.utils.constants.UiConstants

@Composable
fun DrawerContents(
    selectedScreen: RootScreenModel,
    onScreenSelected: (RootScreenModel) -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp)
            .padding(start = 24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        RootScreenModel.entries.forEach {
            DrawerItem(
                model = it,
                isSelected = selectedScreen == it,
                onSelection = onScreenSelected
            )
        }
    }
}

@Composable
fun DrawerItem(
    model: RootScreenModel,
    isSelected: Boolean,
    onSelection: (RootScreenModel) -> Unit
) {
    DrawerItemContainer(
        model = model,
        isSelected = isSelected,
        onSelection = onSelection
    ) {
        DrawerItemIcon(model, isSelected)
        DrawerItemLabel(model, isSelected)
    }
}

// ---- Internals ----
@Composable
private fun DrawerItemIcon(
    model: RootScreenModel,
    isSelected: Boolean
) {
    Icon(
        imageVector = model.icon,
        tint = decideOnTintColor(model, isSelected),
        contentDescription = model.label
    )
}

@Composable
private fun RowScope.DrawerItemLabel(
    model: RootScreenModel,
    isSelected: Boolean
) {
    Text(
        text = model.label,
        color = decideOnTintColor(model, isSelected),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .padding(start = 8.dp)
            .align(Alignment.CenterVertically)
    )
}

@Composable
private fun DrawerItemContainer(
    model: RootScreenModel,
    isSelected: Boolean,
    onSelection: (RootScreenModel) -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    val animatedPrimaryColor by animateColorAsState(
        decideOnPrimaryColor(model, isSelected),
        label = "${model.label}_primary",
        animationSpec = tween(UiConstants.PRIMARY_COLOR_CHANGE_DURATION_MS)
    )
    val animatedSecondaryColor by animateColorAsState(
        decideOnSecondaryColor(model, isSelected),
        label = "${model.label}_secondary",
        animationSpec = tween(UiConstants.SECONDARY_COLOR_CHANGE_DURATION_MS)
    )
    val startPadding by animateDpAsState(
        decideOnStartPadding(isSelected),
        label = "${model.label}_start",
        animationSpec = tween(UiConstants.START_PADDING_CHANGE_DURATION_MS),
    )
    val verticalPadding by animateDpAsState(
        decideOnVerticalPadding(isSelected),
        label = "${model.label}_vertical",
        animationSpec = tween(UiConstants.VERTICAL_PADDING_CHANGE_DURATION_MS)
    )
    val elevation by animateDpAsState(
        decideOnElevation(isSelected),
        label = "${model.label}_elevation"
    )
    Row(
        modifier = Modifier
            .padding(bottom = 8.dp, start = startPadding)
            .shadow(elevation, RoundedCornerShape(topStart = 64.dp, bottomStart = 64.dp))
            .clip(RoundedCornerShape(topStart = 64.dp, bottomStart = 64.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        animatedPrimaryColor,
                        animatedSecondaryColor,
                        animatedSecondaryColor
                    )
                )
            )
            .fillMaxWidth()
            .clickable { onSelection(model) }
            .padding(24.dp)
            .padding(vertical = verticalPadding),
        content = content
    )
}

private fun decideOnPrimaryColor(model: RootScreenModel, isSelected: Boolean) =
    if (isSelected) {
        model.color.primaryColor
    } else {
        DrawerColorCombination.NotSelected.primaryColor
    }

private fun decideOnSecondaryColor(model: RootScreenModel, isSelected: Boolean) =
    if (isSelected) {
        model.color.secondaryColor
    } else {
        DrawerColorCombination.NotSelected.secondaryColor
    }

private fun decideOnTintColor(model: RootScreenModel, isSelected: Boolean) =
    if (isSelected) {
        model.color.labelColor
    } else {
        DrawerColorCombination.NotSelected.labelColor
    }

private fun decideOnStartPadding(isSelected: Boolean) =
    if (isSelected) 0.dp else UiConstants.START_PADDING_NOT_SELECTED.dp

private fun decideOnVerticalPadding(isSelected: Boolean) =
    if (isSelected) UiConstants.VERTICAL_PADDING_SELECTED.dp else 0.dp

private fun decideOnElevation(isSelected: Boolean) =
    if (isSelected) {
        UiConstants.ELEVATION_SELECTED.dp
    } else {
        UiConstants.ELEVATION_NOT_SELECTED.dp
    }

@Composable
@Preview
fun DrawerItem_Preview() {
    DrawerItem(
        model = UiConstants.DEFAULT_ROOT_SCREEN,
        isSelected = true,
        onSelection = {}
    )
}