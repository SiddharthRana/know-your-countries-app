package com.knowyourcountries.presentation.countries

sealed class CountriesListingEvent {
    object Refresh : CountriesListingEvent()
    data class OnSearchQueryChange(val query: String) : CountriesListingEvent()
}