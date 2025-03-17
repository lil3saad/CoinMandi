package com.example.coinmandi.feature_explore.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class GekoSearch (
    @Serializable val coins : List<SearchCoin>?
)
@Serializable
data class SearchCoin(
    val id : String?,
    val name : String?,
    val symbol : String?,
    val large : String?
)