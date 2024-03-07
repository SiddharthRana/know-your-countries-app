package com.knowyourcountries.data.remote.dto

import com.squareup.moshi.Json

data class CitiesStatesListDto(
    @field:Json(name = "error") val error: String?,
    @field:Json(name = "msg") val msg: String?,
    @field:Json(name = "data") val data: List<String> = emptyList(),
)
