package com.example.coinmandi.feature_home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinmandi.feature_home.domain.GetCMuserUsecase
import com.example.coinmandi.feature_home.presentation.state.HomePageEvents
import com.example.coinmandi.feature_home.presentation.state.HomePageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(val   getCMuserUsecase: GetCMuserUsecase
) : ViewModel() {

    private var _pagestate : MutableStateFlow<HomePageState> = MutableStateFlow(HomePageState.idle)
    val pageState = _pagestate.asStateFlow()

    fun onEvent(event : HomePageEvents){
        when(event){
            HomePageEvents.FetchUserData -> {
                viewModelScope.launch {
                   _pagestate.value = getCMuserUsecase.invoke()
                }
            }
            HomePageEvents.LogOutUser -> {}
        }
    }
}