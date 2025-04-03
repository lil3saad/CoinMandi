package com.example.coinmandi.core.feature_coindetail.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ChartData(
  @Serializable val prices: List<List<Double?>?>?
)