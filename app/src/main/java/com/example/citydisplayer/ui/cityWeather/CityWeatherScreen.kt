package com.example.citydisplayer.ui.cityWeather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.citydisplayer.ui.ErrorScreen
import com.example.citydisplayer.ui.LoadingIndicator
import com.example.citydisplayer.ui.theme.Background
import com.example.citydisplayer.ui.theme.PurpleButton
import com.example.citydisplayer.ui.theme.robotoFontFamily

@Composable
fun CityWeatherModelScreen(
    cityName: String,
    lat: Double,
    lon: Double,
    viewModel: CityWeatherViewModel = remember{ mutableStateOf(CityWeatherViewModel(cityName, lat, lon))}.value) {
    // делаю через remember, чтобы при пересобирании composable не вызывался заново init

    val state by viewModel.uiState.collectAsState()
    when (state) {
        is CityWeatherUiState.Success -> {
            WeatherScreen(
                city = viewModel.cityName,
                temp = (state as CityWeatherUiState.Success).temp,
                onClick = viewModel::loadWeather,
            )
        }
        is CityWeatherUiState.Loading -> {
            LoadingIndicator()
        }
        is CityWeatherUiState.Error -> {
            ErrorScreen(
                errorText = (state as CityWeatherUiState.Error).throwable.message ?: "",
                onClick = viewModel::loadWeather
            )
        }
    }
}

@Composable
fun WeatherScreen(city: String = "ararararaarararararararararara", temp: Int = 250, onClick: () -> Unit = {}){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "$temp°C",
                color = Color.Black,
                modifier = Modifier.padding(16.dp, 0.dp),
                fontFamily = robotoFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 57.sp,
                lineHeight = 64.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = city,
                color = Color.Black,
                modifier = Modifier.padding(16.dp, 0.dp),
                fontFamily = robotoFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 32.sp,
                lineHeight = 40.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Button(
            onClick = onClick,
            modifier = Modifier.padding(36.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PurpleButton,
                contentColor = contentColorFor(PurpleButton)
            )
        ) {
            Text(
                text = "Обновить",
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = robotoFontFamily,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
        }

    }
}

@Preview
@Composable
fun WeatherScreenPreview(city: String = "ararararaarararararararararara", temp: Int = 250, onClick: () -> Unit = {}){
    Surface(
        color = Background,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = "$temp°C",
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp, 0.dp),
                    fontFamily = robotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 57.sp,
                    lineHeight = 64.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = city,
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp, 0.dp),
                    fontFamily = robotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 32.sp,
                    lineHeight = 40.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Button(
                onClick = onClick,
                modifier = Modifier.padding(36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PurpleButton,
                    contentColor = contentColorFor(PurpleButton)
                )
            ) {
                Text(
                    text = "Обновить",
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = robotoFontFamily,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
            }
        }
    }
}
