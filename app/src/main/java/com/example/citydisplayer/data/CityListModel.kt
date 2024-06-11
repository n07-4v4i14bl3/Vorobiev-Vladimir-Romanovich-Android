package com.example.citydisplayer.data

import com.example.citydisplayer.util.WebRequests

class CityListModel {
    suspend fun getCityList(): List<City> {
        val data = WebRequests.getCities()
        return data.filter {it.id!=-1 && it.city.isNotBlank()}
            .sortedBy {it.city}

        //List(30){i -> if (i%5==0) "hehe$i" else "i"}
    }
}