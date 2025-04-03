package com.example.coinmandi.core.presentation.navigation

import kotlinx.serialization.Serializable

sealed class CoreDestinations {
    @Serializable
    object AuthNavGraph : CoreDestinations()
    @Serializable
    object CoreNavGraph : CoreDestinations()
    @Serializable
    object HomeNavGraph : CoreDestinations()
    @Serializable
    object ExploreNavGraph : CoreDestinations()
    @Serializable
    object CoinDetailNavGrap : CoreDestinations()
    @Serializable
    object AIBotNavGraph : CoreDestinations()
    @Serializable
    object UserProfileNavGraph : CoreDestinations()
}