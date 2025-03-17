package com.example.coinmandi.feature_explore.presentation.states

import com.example.coinmandi.feature_explore.domain.model.GekoCoin
import com.example.coinmandi.feature_explore.domain.model.TrendingCoins

data class ExplorePageState(
     val UserSearchText : String = "",
     val TrendingListLoading : Boolean = false,
     val TrendingList : TrendingCoins? = null,
     val CategoryListLoading : Boolean = false,
     val IsCateogoryMenuVisible : Boolean = false,
     val SelectedCategory : SelectedCategoryState? = null,
     val CategoryList : List<GekoCoin>? = null
)
