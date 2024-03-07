package com.knowyourcountries.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CountriesListingEntity(
    val name: String?,
    val capital: String?,
    val flag: String?,
    val region: String?,
    val subregion: String?,
    @PrimaryKey val id: Int? = null
)