package com.example.coinmandi.feature_home.presentation.state

import com.example.coinmandi.feature_useronboard.domain.model.CoinMandiUser
sealed  class HomePageState{
   object idle : HomePageState()
   data class ReadUser(val user : CoinMandiUser) : HomePageState()
   data class Error(val message : String) : HomePageState()

}