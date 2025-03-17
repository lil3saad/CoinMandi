package com.example.coinmandi.feature_explore.domain.usecases

import com.example.coinmandi.core.sensitive.GekoRoutes
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoError
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoResult
import com.example.coinmandi.feature_explore.data.repositories.ExploreRepo
import com.example.coinmandi.feature_explore.domain.model.GekoSearch

class SearchCoinUC(val repo : ExploreRepo) {
    suspend operator fun invoke(searchtext : String) : GekoResult< GekoSearch , GekoError>{
        return repo.SearchCoin(
            endpoint = GekoRoutes.baseurl + GekoRoutes.serachEP,
            params = mapOf(
                "query" to searchtext
            ),
            headers = null
        )
    }
}