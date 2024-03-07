package com.knowyourcountries.presentation.country_details

import com.knowyourcountries.domain.model.CountryInfo

data class CountryDetailsState(
    val country: CountryInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
