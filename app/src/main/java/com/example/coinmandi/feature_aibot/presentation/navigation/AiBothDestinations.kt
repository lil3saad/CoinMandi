package com.example.coinmandi.feature_aibot.presentation.navigation

import kotlinx.serialization.Serializable

sealed class AiBothDestinations {
    @Serializable
    class AiBothScreen : AiBothDestinations()
}