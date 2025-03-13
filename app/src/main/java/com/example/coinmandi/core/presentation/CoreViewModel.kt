package com.example.coinmandi.core.presentation

import androidx.lifecycle.ViewModel
import com.example.coinmandi.core.presentation.states.CorePageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CoreViewModel : ViewModel() {
    // Private mutable state
    private val _bottomBarState: MutableStateFlow<CorePageState> = MutableStateFlow(CorePageState.AuthenticationState)
    // Public immutable state
    val bottomBarState: StateFlow<CorePageState> = _bottomBarState

    fun changeAppBarsState(state : CorePageState) {
          _bottomBarState.value = state
    }
}