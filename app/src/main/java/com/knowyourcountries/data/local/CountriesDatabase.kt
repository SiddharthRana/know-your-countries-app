package com.knowyourcountries.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CountriesListingEntity::class],
    version = 2
)
abstract class CountriesDatabase : RoomDatabase() {
    abstract val dao: CountriesDao
}