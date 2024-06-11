package com.example.citydisplayer.util

import com.example.citydisplayer.data.City
import com.example.citydisplayer.data.WeatherData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import kotlin.math.roundToInt

object WebRequests {
    private const val getCitiesUrl = "https://gist.githubusercontent.com/Stronger197/764f9886a1e8392ddcae2521437d5a3b/raw/65164ea1af958c75c81a7f0221bead610590448e/cities.json"
    private val getWeatherUrl = {lat:Double, lon:Double-> "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&units=metric&appid=fc66089342c994391228ed653ec2703f"}

    suspend fun getCities(): List<City>{
        val client = HttpClient(CIO)
        val response = client.get(getCitiesUrl).body<String>()
        client.close()
        //4 объекта с "id": "", а писать отдельный датакласс с стринговым id или кастомный парсер считаю нецелесообразным
        return Json.decodeFromString(response.replace("\"id\": \"\"", "\"id\": \"-1\""))
    }
    suspend fun getWeather(lat:Double, lon:Double): Int{
        val client = HttpClient(CIO)
        val response = client.get(getWeatherUrl(lat, lon)).body<String>()
        client.close()
        val weatherData = Json.decodeFromString<WeatherData>(response)
        return weatherData.main?.temp?.roundToInt() ?: throw Throwable("No data; cod = ${weatherData.cod}")
    }
}

