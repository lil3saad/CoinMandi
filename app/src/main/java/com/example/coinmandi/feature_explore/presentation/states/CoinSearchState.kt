package com.example.coinmandi.feature_explore.presentation.states

import com.example.coinmandi.feature_explore.domain.model.SearchCoin

data class CoinSearchState(
    val IntialState : Boolean = false,
    val Searching : Boolean = false,
    val NoCoinsFound : Boolean = false,
    val EnterProperLenght : Boolean = false,
    val SearchText : String = "",
    val SearchResultList : List<SearchCoin>? = null
)