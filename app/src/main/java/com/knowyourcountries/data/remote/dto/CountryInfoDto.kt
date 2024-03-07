package com.knowyourcountries.data.remote.dto

import com.squareup.moshi.Json

data class CountryInfoDto(
    @field:Json(name = "name") val name: NameDto,
    @field:Json(name = "capital") val capital: List<String>?,
    @field:Json(name = "flags") val flags: FlagsDto,
    @field:Json(name = "coatOfArms") val coatOfArms: CoatOfArmsDto,
    @field:Json(name = "capitalInfo") val capitalInfo: CapitalInfoDto,
    @field:Json(name = "latlng") val latLng: List<Double>?,
    @field:Json(name = "startOfWeek") val startOfWeek: String?,
    @field:Json(name = "independent") val independent: Boolean?,
    @field:Json(name = "landlocked") val landLocked: Boolean?,
    @field:Json(name = "unMember") val unMember: Boolean?,
    @field:Json(name = "region") val region: String?,
    @field:Json(name = "subregion") val subregion: String?,
    @field:Json(name = "borders") val borders: List<String>?,
    @field:Json(name = "area") val area: Double?,
    @field:Json(name = "population") val population: Double?,
    @field:Json(name = "timezones") val timezones: List<String>?,
    @field:Json(name = "continents") val continents: List<String>?,
)

data class NameDto(
    @field:Json(name = "common") val common: String?,
    @field:Json(name = "official") val official: String?,
)

data class FlagsDto(
    @field:Json(name = "png") val png: String?,
)

data class CoatOfArmsDto(
    @field:Json(name = "png") val png: String?,
)

data class CapitalInfoDto(
    @field:Json(name = "latlng") val latLng: List<Double>?
)
