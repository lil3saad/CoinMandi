package com.example.coinmandi.feature_explore.domain

import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.GekoService
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoError
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoResult
import com.example.coinmandi.feature_explore.domain.model.GekoCoin
import com.example.coinmandi.feature_explore.data.repositories.ExploreRepo
import com.example.coinmandi.feature_explore.domain.model.TrendingCoins

class RepoImplementation(
    val gekoService: GekoService
) : ExploreRepo {

    override suspend fun getCategoryList(
        endpoint: String,
        params: Map<String, Any>?,
        headers: Map<String, Any>?
    ): GekoResult<List<GekoCoin>, GekoError> {
        return  gekoService.Get<List<GekoCoin>>(
            endpoint,
            params,
            headers
        )
    }

    override suspend fun getTrendingList(
        endpoint: String,
        params: Map<String, Any>?,
        headers: Map<String, Any>?
    ): GekoResult<TrendingCoins, GekoError> {
        return  gekoService.Get<TrendingCoins>(
            endpoint,
            params,
            headers
        )
    }
}