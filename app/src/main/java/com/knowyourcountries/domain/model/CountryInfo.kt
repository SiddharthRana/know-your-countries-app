package com.knowyourcountries.domain.model

data class CountryInfo(
    val name: Name,
    val capital: List<String>,
    val flags: Flags,
    val coatOfArms: CoatOfArms,
    val capitalInfo: CapitalInfo,
    val latLng: List<Double>,
    val startOfWeek: String,
    val independent: Boolean,
    val landLocked: Boolean,
    val unMember: Boolean,
    val region: String,
    val subregion: String,
    val borders: List<String>,
    val area: Double,
    val population: Double,
    val timezones: List<String>,
    val continents: List<String>,
)

data class Name(
    val common: String,
    val official: String,
)

data class Flags(
    val png: String,
)

data class CoatOfArms(
    val png: String,
)

data class CapitalInfo(
    val latLng: List<Double>
)

