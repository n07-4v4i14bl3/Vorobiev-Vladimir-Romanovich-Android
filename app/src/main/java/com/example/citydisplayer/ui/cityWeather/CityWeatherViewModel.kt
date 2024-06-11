package com.example.citydisplayer.ui.cityWeather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citydisplayer.data.CityWeatherModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CityWeatherViewModel(
    val cityName: String,
    val latitude: Double,
    val longitude: Double,
    private val model: CityWeatherModel = CityWeatherModel()
) : ViewModel() {

    // начальное значение - ошибка: если не запустился init, это определенно ошибка
    private val _uiState: MutableStateFlow<CityWeatherUiState> = MutableStateFlow(CityWeatherUiState.Error(
        Throwable("lagged")
    ))
    val uiState: StateFlow<CityWeatherUiState> = _uiState.asStateFlow()

    init{
        loadWeather()
    }

    fun loadWeather() {
        if (_uiState.value !is CityWeatherUiState.Loading){
            _uiState.value = CityWeatherUiState.Loading
            viewModelScope.launch {
                try {
                    val temp = model.getWeatherData(latitude, longitude)
                    _uiState.value = CityWeatherUiState.Success(temp)
                } catch (throwable: Throwable){
                    _uiState.value = CityWeatherUiState.Error(throwable)
                }
            }
        }
    }
}

sealed interface CityWeatherUiState {
    object Loading : CityWeatherUiState
    data class Error(val throwable: Throwable) : CityWeatherUiState
    data class Success(val temp: Int) : CityWeatherUiState
}