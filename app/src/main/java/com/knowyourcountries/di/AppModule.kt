package com.knowyourcountries.di

import android.app.Application
import androidx.room.Room
import com.knowyourcountries.data.local.CountriesDatabase
import com.knowyourcountries.data.remote.CountriesApi
import com.knowyourcountries.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCountriesApi(): CountriesApi {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(Constants.REST_COUNTRIES_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideCountriesDatabase(app: Application): CountriesDatabase {
        return Room.databaseBuilder(
            app,
            CountriesDatabase::class.java,
            "countries-db.db"
        ).build()
    }
}