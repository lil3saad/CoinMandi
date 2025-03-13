package com.example.coinmandi.feature_useronboard.presentation.userregistration.state

import com.example.coinmandi.feature_useronboard.domain.model.CoinMandiUser

sealed class CMUserState {

    // Or Can BE Phone User

    object Loading : CMUserState()
    object idle : CMUserState()

    object GoogleLinkSuccess : CMUserState()
    object UserCreated : CMUserState()

    // To check If Google Signed In User is Already Present in FireStore
    data class Read(val user : CoinMandiUser) : CMUserState()
    data class Error(val message : String) : CMUserState()
    data class FetchUserError(val message : String) : CMUserState()

}