package com.knowyourcountries.presentation.countries

import com.knowyourcountries.domain.model.CountriesListing

data class CountriesListingState(
    val countries: List<CountriesListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val regionOrSubRegion: String = "",
    val error: String? = null,
)
