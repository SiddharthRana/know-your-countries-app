package com.knowyourcountries.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountriesListing(
        countriesListingEntity: List<CountriesListingEntity>
    )

    @Query("DELETE FROM countrieslistingentity")
    suspend fun clearCountriesListing()

    @Query(
        """
            SELECT *
            FROM countrieslistingentity
            WHERE (:regionOrSubRegion = '' OR region = :regionOrSubRegion)
            || (:regionOrSubRegion = '' OR subregion = :regionOrSubRegion)
            AND LOWER(name || capital) LIKE '%' || LOWER(:query) || '%'
        """
    )
    suspend fun searchCountriesListing(query: String = "", regionOrSubRegion : String = ""): List<CountriesListingEntity>
}