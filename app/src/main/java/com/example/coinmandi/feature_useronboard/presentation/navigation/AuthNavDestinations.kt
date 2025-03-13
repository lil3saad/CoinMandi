package com.example.coinmandi.feature_useronboard.presentation.navigation

import kotlinx.serialization.Serializable

sealed class AuthNavDestinations {
    // AuthScreens
    @Serializable
    object AuthPage : AuthNavDestinations()
    @Serializable
    object SignupPage : AuthNavDestinations()
    @Serializable
    data class LoginPage(val firstname : String  ,val age : Int ) : AuthNavDestinations()
    @Serializable
    object Registration : AuthNavDestinations()

}
