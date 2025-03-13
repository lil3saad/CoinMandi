package com.example.coinmandi.feature_useronboard.presentation.userregistration.state

import com.example.coinmandi.feature_useronboard.domain.model.CoinMandiUser


sealed class UserRegistrationEvents {
    object FetchUser : UserRegistrationEvents()
    data class CreateUser(val user : CoinMandiUser) : UserRegistrationEvents()
    data class LinkGoogleAccountWith( val user : CoinMandiUser ) : UserRegistrationEvents()
   data class LinkEmailPasswordToGoogle(val user: CoinMandiUser , val password : String) : UserRegistrationEvents()

    // Form Events
    data class ChangeUserName(val value : String) : UserRegistrationEvents()
    data class ChangeRealName(val value : String) : UserRegistrationEvents()
    data class ChangePasswordName(val value : String) : UserRegistrationEvents()
    data class ChangeRepeatPasswordName(val value : String) : UserRegistrationEvents()

    object ValidateGoogleUserForm : UserRegistrationEvents()
    object ValidateEmailUserForm : UserRegistrationEvents()
}