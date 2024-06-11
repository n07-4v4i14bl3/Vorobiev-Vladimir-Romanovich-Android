package com.example.citydisplayer

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.example.citydisplayer.ui.cityWeather.CityWeatherModelScreen
import com.example.citydisplayer.ui.citylist.CityListModelScreen
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: String = AppDestinations.CITY_LIST,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AppDestinations.CITY_LIST) {
            CityListModelScreen(navController)
        }
        composable(AppDestinations.CITY_WEATHER+"/{cityName}/{lat}/{lon}"){backStackEntry ->
            CityWeatherModelScreen(
                cityName = backStackEntry.arguments?.getString("cityName")?:"",
                lat = backStackEntry.arguments?.getString("lat")?.toDouble()?:0.0,
                lon = backStackEntry.arguments?.getString("lon")?.toDouble()?:0.0
            )
        }
    }
}