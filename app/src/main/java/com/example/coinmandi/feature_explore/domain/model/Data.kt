package com.example.coinmandi.feature_explore.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val content: Content?,
    val market_cap: String?,
    val market_cap_btc: String?,
    val price: Double?,
    val price_btc: String?,
    val price_change_percentage_24h: PriceChangePercentage24h?,
    val sparkline: String?,
    val total_volume: String?,
    val total_volume_btc: String?
)