package com.example.coinmandi.core.feature_coindetail.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CoinMarketDetail(
    val id : String,
    val symbol : String,
    val name : String ,
    val description : Description,
    val links : Link,
    val image : Image,
    val sentiment_votes_up_percentage : Double,
    val sentiment_votes_down_percentage : Double,
    val market_data : MarketData
)
@Serializable
data class MarketData(
    val current_price : Usd,
    val ath : Usd,
    val atl : Usd,
    val high_24h : Usd,
    val low_24h : Usd,
    val market_cap : Usd,
    val market_cap_rank : Long,
    val fully_diluted_valuation : Usd,
    val total_volume : Usd,
    val price_change_percentage_24h : Double
)
@Serializable
data class Usd(
    val usd : Double
)
@Serializable
data class Description(
    val en : String
)
@Serializable
data class Link(
    val homepage : List<String>?,
    val subreddit_url : String? // Open this In the Reddit Android App
)
@Serializable
data class Image(
    val large : String
)
