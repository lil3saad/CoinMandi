package com.example.coinmandi.userAuthentication.presentation.signup.state

sealed class SignUpEvents {
    object GoogleAuth : SignUpEvents()
    data class EmailSignUp(val email : String , val password: String) : SignUpEvents()

    data class changeEmailfieldValue(val value : String) : SignUpEvents()
    data class changePasswordfieldValue(val value : String) : SignUpEvents()
    data class changeRepeatPasswordfieldValue(val value : String) : SignUpEvents()

    data class ChangeError(val msg : String) : SignUpEvents()

    object ValidateForm : SignUpEvents()
}