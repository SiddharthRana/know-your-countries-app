package com.knowyourcountries.presentation.country_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.hilt.getViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.knowyourcountries.util.Helper.formatNumber

data class CountryDetailsScreen(
    val name: String,
    val capital: String,
) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @OptIn(ExperimentalVoyagerApi::class, ExperimentalGlideComposeApi::class)
    @Composable
    override fun Content() {
        val viewModel: CountryDetailsViewModel =
            getViewModel<CountryDetailsViewModel, CountryDetailsViewModel.Factory> { factory ->
                factory.create(name)
            }
        val state = viewModel.state
        if (state.error == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                state.country?.let { country ->
                    Row {
                        Box(
                            modifier = Modifier
                                .size(100.dp, 66.dp)
                        ) {
                            GlideImage(
                                model = country.flags.png,
                                contentDescription = country.name.official,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Column(
                            modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp)
                        ) {
                            Text(
                                text = name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = capital,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            vertical = 16.dp
                        )
                    )
                    Row {
                        state.country.coatOfArms.png.let {
                            Box(
                                modifier = Modifier
                                    .size(40.dp, 40.dp)
                                    .padding(0.dp, 0.dp, 16.dp, 0.dp)
                            ) {
                                GlideImage(
                                    model = country.coatOfArms.png,
                                    contentDescription = country.name.official,
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                        Text(
                            text = state.country.name.official,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            vertical = 16.dp
                        )
                    )
                    Column {
                        Text(
                            text = "Region: ${state.country.region}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Sub Region: ${state.country.subregion}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            vertical = 16.dp
                        )
                    )
                    Column {
                        Text(
                            text = "Population: " +
                                    "${formatNumber(state.country.population)}(approx.) " +
                                    "${state.country.population.toInt()} (abs.)",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Area: ${state.country.area} km²",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            vertical = 16.dp
                        )
                    )
                    Column {
                        Text(
                            text = "Timezone/s: ${state.country.timezones.joinToString(", ")}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            vertical = 16.dp
                        )
                    )
                    Column {
                        Text(
                            text = "Continent/s: ${state.country.continents.joinToString(", ")}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            vertical = 16.dp
                        )
                    )
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.error != null) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }

}
