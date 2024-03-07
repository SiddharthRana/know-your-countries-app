package com.knowyourcountries.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.knowyourcountries.data.local.CountriesDatabase
import com.knowyourcountries.data.mapper.convertCountryInfoToList
import com.knowyourcountries.data.mapper.processCountriesEntity
import com.knowyourcountries.data.mapper.toCountriesListing
import com.knowyourcountries.data.mapper.toCountryInfo
import com.knowyourcountries.data.remote.CountriesApi
import com.knowyourcountries.domain.model.CountriesListing
import com.knowyourcountries.domain.model.CountryInfo
import com.knowyourcountries.domain.repository.CountriesRepository
import com.knowyourcountries.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Singleton
class CountriesRepositoryImpl @Inject constructor(
    private val api: CountriesApi,
    db: CountriesDatabase,
) : CountriesRepository {

    private val dao = db.dao

    override suspend fun getCountriesListing(
        query: String,
        regionOrSubRegion: String,
        fetchFromRemote: Boolean,
    ): Flow<Resource<List<CountriesListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localCountries = dao.searchCountriesListing(query, regionOrSubRegion)
            emit(Resource.Success(data = localCountries.map { it.toCountriesListing() }))

            val isDbEmpty = localCountries.isEmpty() && query.isBlank()
            val shouldLoadFromLocal = !isDbEmpty && !fetchFromRemote
            if (shouldLoadFromLocal) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteCountryListing = try {
                val response = api.getAllCountries()
                val newResp = response.map {
                    processCountriesEntity(
                        it.toCountryInfo()
                    )
                }
                newResp
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(e.message.toString()))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(e.message.toString()))
                null
            }

            remoteCountryListing?.let { countriesListings ->
                dao.clearCountriesListing()
                dao.insertCountriesListing(countriesListings)
                emit(
                    Resource.Success(data = dao.searchCountriesListing(regionOrSubRegion = regionOrSubRegion)
                        .map { it.toCountriesListing() })
                )
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getCountryDetails(countryName: String): Resource<List<CountryInfo>> {
        return try {
            val response = api.getCountryDetails(countryName)
            val result =
                response.firstOrNull()?.let { convertCountryInfoToList(it.toCountryInfo()) }
            Resource.Success(
                data = result
            )
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = e.message.toString()
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = e.message.toString()
            )
        }
    }

}