package com.example.coinmandi.feature_home.presentation.state

sealed class HomePageEvents {
    object FetchUserData : HomePageEvents()
    object LogOutUser : HomePageEvents()
}