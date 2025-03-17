package com.example.coinmandi.feature_explore.data.repositories

import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoError
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoResult
import com.example.coinmandi.feature_explore.domain.model.GekoCoin
import com.example.coinmandi.feature_explore.domain.model.GekoSearch
import com.example.coinmandi.feature_explore.domain.model.TrendingCoins

interface ExploreRepo {

     suspend fun  getCategoryList(endpoint : String,
                                  params : Map<String, Any>?,
                                  headers : Map<String , Any>?
     ) : GekoResult<List<GekoCoin> , GekoError>

     suspend fun  getTrendingList(endpoint : String,
                                  params : Map<String, Any>?,
                                  headers : Map<String , Any>?
     ) : GekoResult<TrendingCoins , GekoError>

    suspend fun SearchCoin(
        endpoint : String,
        params : Map<String, Any>?,
        headers : Map<String , Any>?
    ) : GekoResult<GekoSearch , GekoError>
}