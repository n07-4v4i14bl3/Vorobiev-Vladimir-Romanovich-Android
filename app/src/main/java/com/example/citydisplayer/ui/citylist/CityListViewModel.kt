package com.example.citydisplayer.ui.citylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.citydisplayer.AppDestinations
import com.example.citydisplayer.data.City
import com.example.citydisplayer.data.CityListModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CityListViewModel(
    private val navController: NavController,
    private val model: CityListModel = CityListModel()
) : ViewModel() {

    // начальное значение - ошибка: если не запустился init, это определенно ошибка
    private val _uiState: MutableStateFlow<CityListUiState> = MutableStateFlow(CityListUiState.Error(
        Throwable("lagged")
    ))
    val uiState: StateFlow<CityListUiState> = _uiState.asStateFlow()

    init{
        loadCityList()
    }

    fun loadCityList() {
        if (_uiState.value !is CityListUiState.Loading){
            _uiState.value = CityListUiState.Loading
            viewModelScope.launch {
                try {
                    val temp = model.getCityList()
                    if (temp.isEmpty()) throw Throwable("empty data")
                    _uiState.value = CityListUiState.Success(temp)
                } catch (throwable: Throwable){
                    _uiState.value = CityListUiState.Error(throwable)
                }
            }
        }
    }

    fun gotoForecastScreen(cityId: Int){
        val city = (_uiState.value as CityListUiState.Success).data[cityId]
        navController.navigate(AppDestinations.CITY_WEATHER + "/${city.city}/${city.latitude}/${city.longitude}")
    }
}

sealed interface CityListUiState {
    object Loading : CityListUiState
    data class Error(val throwable: Throwable) : CityListUiState
    data class Success(val data: List<City>) : CityListUiState
}