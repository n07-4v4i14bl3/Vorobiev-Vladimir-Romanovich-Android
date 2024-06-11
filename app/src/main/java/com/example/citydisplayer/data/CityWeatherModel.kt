package com.example.citydisplayer.data

import com.example.citydisplayer.util.WebRequests

class CityWeatherModel {
    suspend fun getWeatherData(lat:Double, lon:Double): Int {
        return WebRequests.getWeather(lat, lon)
    }
}