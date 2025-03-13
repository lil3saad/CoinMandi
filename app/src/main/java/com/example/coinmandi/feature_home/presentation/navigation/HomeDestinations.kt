package com.example.coinmandi.feature_home.presentation.navigation

import kotlinx.serialization.Serializable

sealed class HomeDestinations {
    // App Screens
    @Serializable
    object HomeScreen : HomeDestinations()
}