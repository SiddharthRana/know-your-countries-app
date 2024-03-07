package com.knowyourcountries.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knowyourcountries.domain.model.MainScreenData
import com.knowyourcountries.util.Constants.ALL_COUNTRIES
import com.knowyourcountries.util.Constants.EXPANSION_ANIMATION_DURATION
import com.knowyourcountries.util.Helper.getSubRegionsFromRegion
import com.knowyourcountries.util.Helper.mapStringListToDataClass

@Composable
fun MainPageList(
    data: MainScreenData,
    expanded: Boolean,
    onClickExpanded: (id: Int) -> Unit,
    onClick: (regionOrSubregion: String) -> Unit,
) {
    val transition = updateTransition(targetState = expanded, label = "transition")
    val iconRotationDeg by transition.animateFloat(label = "icon rotate") { state ->
        if (state) {
            0f
        } else {
            180f
        }
    }
    MainListItem(
        data,
        iconRotationDeg,
        onClickExpanded,
        onClick,
        modifier = Modifier.padding(horizontal = 0.dp),
        isSubRegion = false,
    )
    getSubRegionsFromRegion[data.regionOrSubRegion]?.let {
        ExpandableList(
            isExpanded = expanded,
            subRegionList = it,
            onClick = onClick
        )
    }
}

@Composable
fun ExpandableList(
    isExpanded: Boolean,
    subRegionList: List<String>,
    onClick: (regionOrSubregion: String) -> Unit,
) {
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(EXPANSION_ANIMATION_DURATION)
        ) + fadeIn(initialAlpha = .3f, animationSpec = tween(EXPANSION_ANIMATION_DURATION))
    }
    val exitTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(EXPANSION_ANIMATION_DURATION)
        ) + fadeOut(animationSpec = tween(EXPANSION_ANIMATION_DURATION))
    }

    AnimatedVisibility(
        visible = isExpanded,
        enter = enterTransition,
        exit = exitTransition
    ) {
        val subRegions = mapStringListToDataClass(subRegionList)
        Column {
            subRegions.forEach { subRegion ->
                MainListItem(
                    data = subRegion,
                    onClick = {
                        onClick(subRegion.regionOrSubRegion)
                    },
                    showDropDownIcon = false,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    isSubRegion = true
                )
            }
        }
    }
}

@Composable
fun MainListItem(
    data: MainScreenData,
    iconRotationDeg: Float = 0f,
    onClickExpanded: (id: Int) -> Unit = {},
    onClick: (regionOrSubregion: String) -> Unit,
    showDropDownIcon: Boolean = true,
    modifier: Modifier,
    isSubRegion: Boolean,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxSize()
            .clickable {
                onClick(data.regionOrSubRegion)
            },
    ) {
        Text(
            text = data.regionOrSubRegion,
            fontWeight = if (isSubRegion) {
                FontWeight.SemiBold
            } else {
                FontWeight.Bold
            },
            fontSize = if (isSubRegion) {
                16.sp
            } else {
                18.sp
            },
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(vertical = 24.dp)
        )
        if (showDropDownIcon && data.regionOrSubRegion != ALL_COUNTRIES) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Arrow Icon",
                modifier = Modifier
                    .rotate(iconRotationDeg)
                    .clickable {
                        onClickExpanded(data.id)
                    }
                    .align(Alignment.CenterVertically)
            )
        }
    }
    HorizontalDivider(modifier = modifier)
}