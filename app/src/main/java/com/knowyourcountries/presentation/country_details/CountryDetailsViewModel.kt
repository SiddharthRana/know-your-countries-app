package com.knowyourcountries.presentation.country_details

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
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CountryDetailsViewModel.Factory::class)
class CountryDetailsViewModel @AssistedInject constructor(
    @Assisted val name: String,
    private val repository: CountriesRepository
) : ViewModel() {

    var state by mutableStateOf(CountryDetailsState())

    init {
        getCountryDetails()
    }

    @AssistedFactory
    interface Factory {
        fun create(name: String): CountryDetailsViewModel
    }

    private fun getCountryDetails() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val countryInfoResult = async { repository.getCountryDetails(name) }
            when (val result = countryInfoResult.await()) {
                is Resource.Error -> {
                    state = state.copy(
                        country = null,
                        isLoading = false,
                        error = result.message
                    )
                }

                is Resource.Success -> {
                    state = state.copy(
                        country = result.data?.firstOrNull(),
                        isLoading = false,
                        error = null
                    )
                }

                else -> Unit
            }
        }
    }
}