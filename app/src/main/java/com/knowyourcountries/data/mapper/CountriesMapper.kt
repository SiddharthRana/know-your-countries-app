package com.knowyourcountries.data.mapper

import com.knowyourcountries.data.local.CountriesListingEntity
import com.knowyourcountries.data.remote.dto.CapitalInfoDto
import com.knowyourcountries.data.remote.dto.CoatOfArmsDto
import com.knowyourcountries.data.remote.dto.CountryInfoDto
import com.knowyourcountries.data.remote.dto.FlagsDto
import com.knowyourcountries.data.remote.dto.NameDto
import com.knowyourcountries.domain.model.CapitalInfo
import com.knowyourcountries.domain.model.CoatOfArms
import com.knowyourcountries.domain.model.CountriesListing
import com.knowyourcountries.domain.model.CountryInfo
import com.knowyourcountries.domain.model.Flags
import com.knowyourcountries.domain.model.Name

fun processCountriesEntity(countryInfo: CountryInfo): CountriesListingEntity {
    return CountriesListingEntity(
        name = countryInfo.name.common,
        capital = countryInfo.capital.firstOrNull(),
        flag = countryInfo.flags.png,
        region = countryInfo.region,
        subregion = countryInfo.subregion,
    )
}

fun CountriesListingEntity.toCountriesListing(): CountriesListing {
    return CountriesListing(
        name = name ?: "",
        capital = capital ?: "",
        flag = flag ?: "",
        region = region ?: "",
        subregion = subregion ?: "",
    )
}

fun CountriesListing.toCountriesListingEntity(): CountriesListingEntity {
    return CountriesListingEntity(
        name = name,
        capital = capital,
        flag = flag,
        region = region,
        subregion = subregion,
    )
}

fun convertCountryInfoToList(countryInfo: CountryInfo): List<CountryInfo> {
    val list = mutableListOf<CountryInfo>()
    list.add(countryInfo)
    return list
}

fun CountryInfoDto.toCountryInfo(): CountryInfo {
    return CountryInfo(
        name = name.toName(),
        capital = capital ?: emptyList(),
        flags = flags.toFlags(),
        coatOfArms = coatOfArms.toCoatOfArms(),
        capitalInfo = capitalInfo.toCapitalInfo(),
        latLng = latLng ?: emptyList(),
        startOfWeek = startOfWeek ?: "",
        independent = independent ?: false,
        landLocked = landLocked ?: false,
        unMember = unMember ?: false,
        region = region ?: "",
        subregion = subregion ?: "",
        borders = borders ?: emptyList(),
        area = area ?: 0.0,
        population = population ?: 0.0,
        timezones = timezones ?: emptyList(),
        continents = continents ?: emptyList(),
    )
}

fun NameDto.toName(): Name {
    return Name(
        common = common ?: "",
        official = official ?: ""
    )
}

fun FlagsDto.toFlags(): Flags {
    return Flags(
        png = png ?: "",
    )
}

fun CoatOfArmsDto.toCoatOfArms(): CoatOfArms {
    return CoatOfArms(
        png = png ?: "",
    )
}

fun CapitalInfoDto.toCapitalInfo(): CapitalInfo {
    return CapitalInfo(
        latLng = latLng ?: emptyList(),
    )
}