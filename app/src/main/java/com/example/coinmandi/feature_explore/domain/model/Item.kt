package com.example.coinmandi.feature_explore.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val coin_id: Int,
    val `data`: Data?,
    val id: String?,
    val large: String?,
    val market_cap_rank: Int?,
    val name: String?,
    val price_btc: Double?,
    val score: Int?,
    val slug: String?,
    val small: String?,
    val symbol: String?,
    val thumb: String?
)