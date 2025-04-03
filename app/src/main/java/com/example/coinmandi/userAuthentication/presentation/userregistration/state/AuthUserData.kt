package com.example.coinmandi.userAuthentication.presentation.userregistration.state

import com.example.coinmandi.userAuthentication.domain.model.EmailUserData
import com.example.coinmandi.userAuthentication.domain.model.GoogleUserData

data class AuthUserData (
    val EmailUser : EmailUserData? = null,
    val GoogleUser : GoogleUserData? = null
)