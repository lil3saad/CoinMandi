package com.example.coinmandi.core.feature_coindetail.domain.usecases

import com.example.coinmandi.core.feature_coindetail.domain.model.CoinMarketDetail
import com.example.coinmandi.core.sensitive.GekoRoutes
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoError
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoResult
import com.example.coinmandi.feature_explore.data.repositories.ExploreRepo

class GetCoinDetailsUc(val repo : ExploreRepo) {
    suspend operator fun invoke(coinid : String) : GekoResult<CoinMarketDetail , GekoError>{
        return repo.GetCoinDetail(
            endpoint = GekoRoutes.CoinDetailEp + coinid,
            params = null,
            headers = null
        )
    }
}