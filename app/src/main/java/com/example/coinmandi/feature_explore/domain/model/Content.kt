package com.example.coinmandi.feature_explore.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Content(
    val description: String?,
    val title: String?
)