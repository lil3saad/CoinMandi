package com.example.coinmandi.feature_explore.domain.usecases

import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoError
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoResult
import com.example.coinmandi.feature_explore.data.repositories.ExploreRepo
import com.example.coinmandi.feature_explore.domain.model.TrendingCoins

class GetTrendingListUC(val repo: ExploreRepo) {

    suspend operator fun invoke() : GekoResult<TrendingCoins , GekoError> {
        return repo.getTrendingList(
            endpoint = "https://api.coingecko.com/api/v3/search/trending",
            params = null,
            headers = null
        )
    }
}