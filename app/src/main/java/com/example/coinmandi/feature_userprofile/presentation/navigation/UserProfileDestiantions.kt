package com.example.coinmandi.feature_userprofile.presentation.navigation

import kotlinx.serialization.Serializable

sealed class UserProfileDestiantions {
   @Serializable
    class UserProfilePage() : UserProfileDestiantions()
}