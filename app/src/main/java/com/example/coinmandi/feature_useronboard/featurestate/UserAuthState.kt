package com.example.coinmandi.feature_useronboard.featurestate

import com.example.coinmandi.feature_useronboard.domain.model.EmailUserData
import com.example.coinmandi.feature_useronboard.domain.model.GoogleUserData

sealed class UserAuthState {

    object Loggedin : UserAuthState()
    object LoggedOut : UserAuthState()

    object idle : UserAuthState()
    object Loading: UserAuthState()

    // Auth SignUPs
    data class GoogleUserSuccess(val userdata : GoogleUserData) : UserAuthState()
    data class EmailUserSuccess(val userData: EmailUserData) :  UserAuthState()

    object LoginSucces : UserAuthState()
    data class SignUpError(val msg : String )  : UserAuthState()
    data class LoginError(val msg : String )  : UserAuthState()
}