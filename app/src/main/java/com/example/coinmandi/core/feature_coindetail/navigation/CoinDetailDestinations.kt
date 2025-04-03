package com.example.coinmandi.core.feature_coindetail.navigation

import kotlinx.serialization.Serializable

sealed class CoinDetailDestinations {
  @Serializable
  data class CoinDetailPage(val coinid : String) : CoinDetailDestinations()
}