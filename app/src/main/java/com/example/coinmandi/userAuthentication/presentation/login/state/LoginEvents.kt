package com.example.coinmandi.userAuthentication.presentation.login.state

sealed class LoginPageEvents {
    data class Login(val email : String , val password : String) : LoginPageEvents()
    object IntiateGoogleAuth : LoginPageEvents()

    data class ChangeEmailValue(val value : String) : LoginPageEvents()
    data class ChangePasswordValue(val value : String) : LoginPageEvents()
    data class ChangeErrorMessage (val msg : String) : LoginPageEvents()
    object ValidateForm : LoginPageEvents()
}