package com.knowyourcountries.presentation.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.knowyourcountries.domain.model.MainScreenData
import com.knowyourcountries.presentation.countries.CountriesListingScreen
import com.knowyourcountries.util.Constants.ALL_COUNTRIES
import com.knowyourcountries.util.Constants.REGIONS
import com.knowyourcountries.util.Helper.mapStringListToDataClass

class MainScreen : Screen {

    @Composable
    override fun Content() {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            val regions = mapStringListToDataClass(REGIONS)
            items(regions) { region ->
                NavigateToScreen(region = region)
            }
        }
    }

    @Composable
    private fun NavigateToScreen(region: MainScreenData) {
        val navigator = LocalNavigator.currentOrThrow

        var expandedItem by remember {
            mutableIntStateOf(-1)
        }

        MainPageList(
            data = region,
            expanded = expandedItem == region.id,
            onClickExpanded = { id ->
                expandedItem = if (expandedItem == id) {
                    -1
                } else {
                    id
                }
            },
            onClick = { regionOrSubRegion ->
                navigator.push(
                    CountriesListingScreen(
                        if (regionOrSubRegion == ALL_COUNTRIES) {
                            ""
                        } else {
                            regionOrSubRegion
                        },
                    )
                )
            }
        )
    }
}