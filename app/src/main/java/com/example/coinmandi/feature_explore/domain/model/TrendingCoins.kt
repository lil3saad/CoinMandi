package com.example.coinmandi.feature_explore.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TrendingCoins(
  val coins: List<Coin>?
)