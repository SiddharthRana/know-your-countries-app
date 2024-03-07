package com.knowyourcountries.data.remote

import com.knowyourcountries.data.remote.dto.CitiesStatesBodyDto
import com.knowyourcountries.data.remote.dto.CitiesStatesListDto
import com.knowyourcountries.data.remote.dto.CountryInfoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

interface CountriesApi {

    @GET("all?fields=name,capital,flags,coatOfArms,capitalInfo,region,subregion")
    suspend fun getAllCountries(): List<CountryInfoDto>

    @GET("name/{country}?fields=name,capital,flags,languages,independent,unMember,currencies,region,subregion,latlng,population,car,timezones,continents,coatOfArms,startOfWeek,capitalInfo,landlocked,borders,area")
    suspend fun getCountryDetails(@Path("country") country: String): List<CountryInfoDto>

    @GET("region/{region}")
    suspend fun getCountryRegion(@Path("region") country: String): List<CountryInfoDto>

    @GET("subregion/{subregion}")
    suspend fun getCountrySubRegion(@Path("subregion") country: String): List<CountryInfoDto>

    @POST
    suspend fun getCountryStates(
        @Url url: String,
        @Body states: CitiesStatesBodyDto
    ): CitiesStatesListDto

    @POST
    suspend fun getCountryCities(
        @Url url: String,
        @Body cities: CitiesStatesBodyDto
    ): CitiesStatesListDto

    @POST
    suspend fun getCitiesInState(
        @Url url: String,
        @Body cities: CitiesStatesBodyDto
    ): CitiesStatesListDto
}