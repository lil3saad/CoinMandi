package com.example.coinmandi.feature_explore.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PriceChangePercentage24h(
    val usd: Double?
)