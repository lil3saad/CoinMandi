package com.example.coinmandi.feature_useronboard.presentation.userregistration.state

import com.example.coinmandi.feature_useronboard.domain.model.EmailUserData
import com.example.coinmandi.feature_useronboard.domain.model.GoogleUserData

data class AuthUserData (
    val EmailUser : EmailUserData? = null,
    val GoogleUser : GoogleUserData? = null
)