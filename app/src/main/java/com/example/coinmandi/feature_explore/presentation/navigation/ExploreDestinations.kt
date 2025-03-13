package com.example.coinmandi.feature_explore.presentation.navigation

import kotlinx.serialization.Serializable

sealed class ExploreDestinations {
   @Serializable
   class ExplorePage : ExploreDestinations()
}