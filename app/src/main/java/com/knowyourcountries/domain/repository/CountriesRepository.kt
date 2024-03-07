package com.knowyourcountries.domain.repository

import com.knowyourcountries.domain.model.CountriesListing
import com.knowyourcountries.domain.model.CountryInfo
import com.knowyourcountries.util.Resource
import kotlinx.coroutines.flow.Flow

interface CountriesRepository {
    suspend fun getCountriesListing(
        query: String,
        regionOrSubRegion: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<CountriesListing>>>

    suspend fun getCountryDetails(countryName: String): Resource<List<CountryInfo>>
}