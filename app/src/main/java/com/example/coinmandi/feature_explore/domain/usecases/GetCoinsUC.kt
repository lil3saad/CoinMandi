package com.example.coinmandi.feature_explore.domain.usecases

import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoError
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoResult
import com.example.coinmandi.core.sensitive.GekoRoutes
import com.example.coinmandi.feature_explore.data.repositories.ExploreRepo
import com.example.coinmandi.feature_explore.domain.model.GekoCoin

class GetCoinsUC(private val repo : ExploreRepo) {
    suspend operator fun invoke(category : String) : GekoResult<List<GekoCoin>, GekoError> {
        return repo.getCategoryList(
            endpoint = GekoRoutes.baseurl + GekoRoutes.getcoinsDataEP,
            params = mapOf<String, Any>(
                "vs_currency" to "usd" ,
                "category" to category,
                "order" to "market_cap_desc"
            ),
            headers = null
        )
    }
}