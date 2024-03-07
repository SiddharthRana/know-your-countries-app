package com.knowyourcountries.util

import com.knowyourcountries.domain.model.MainScreenData
import kotlin.math.absoluteValue

object Helper {

    val getSubRegionsFromRegion = mapOf(
        "Africa" to listOf(
            "Eastern Africa",
            "Western Africa",
            "Northern Africa",
            "Middle Africa",
            "Southern Africa",
        ),
        "Americas" to listOf(
            "Central America",
            "North America",
            "South America",
            "Caribbean",
        ),
        "Asia" to listOf(
            "Eastern Asia",
            "Central Asia",
            "South-Eastern Asia",
            "Southern Asia",
            "Western Asia",
        ),
        "Europe" to listOf(
            "Southern Europe",
            "Eastern Europe",
            "Central Europe",
            "Western Europe",
            "Southeast Europe",
            "Northern Europe",
        ),
        "Oceania" to listOf(
            "Polynesia",
            "Micronesia",
            "Melanesia",
            "Australia and New Zealand",
        ),
    )

    fun mapStringListToDataClass(regionsOrSubregions: List<String>): List<MainScreenData> {
        val mainPageDataList = mutableListOf<MainScreenData>()
        regionsOrSubregions.forEachIndexed { index, regionOrSubRegion ->
            mainPageDataList.add(MainScreenData(index, regionOrSubRegion))
        }
        return mainPageDataList
    }

    fun formatNumber(value: Double): String {
        val absValue = value.absoluteValue
        return when {
            absValue >= 1_000_000 -> "%.2fM".format(value / 1_000_000)
            absValue >= 1_000 -> "%.2fK".format(value / 1_000)
            else -> "%.2f".format(value)
        }
    }

}