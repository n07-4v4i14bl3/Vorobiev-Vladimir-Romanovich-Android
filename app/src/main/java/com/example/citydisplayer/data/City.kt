package com.example.citydisplayer.data

import kotlinx.serialization.Serializable

@Serializable
data class City(val id: Int, val city: String, val latitude: Double, val longitude: Double)
// в джсоне есть 4 города с пустым id