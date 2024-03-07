package com.knowyourcountries.presentation.countries

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knowyourcountries.domain.repository.CountriesRepository
import com.knowyourcountries.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CountriesListingViewModel.Factory::class)
class CountriesListingViewModel @AssistedInject constructor(
    private val repository: CountriesRepository,
    @Assisted val regionOrSubRegion: String,
) : ViewModel() {

    var state by mutableStateOf(CountriesListingState())

    private var searchJob: Job? = null

    init {
        state = state.copy(regionOrSubRegion = regionOrSubRegion)
        getCountriesListing()
    }

    @AssistedFactory
    interface Factory {
        fun create(regionOrSubRegion: String): CountriesListingViewModel
    }

    fun onEvent(event: CountriesListingEvent) {
        when (event) {
            is CountriesListingEvent.Refresh -> {
                getCountriesListing(fetchFromRemote = true, regionOrSubRegion = state.regionOrSubRegion)
                state = state.copy(searchQuery = "")
            }
            is CountriesListingEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCountriesListing()
                }
            }
        }
    }

    private fun getCountriesListing(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false,
        regionOrSubRegion: String = state.regionOrSubRegion,
    ) {
        viewModelScope.launch {
            repository
                .getCountriesListing(query, regionOrSubRegion, fetchFromRemote)
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { countriesListings ->
                                state = state.copy(
                                    countries = countriesListings.sortedBy { it.name }
                                )
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }
}