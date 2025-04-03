package com.example.coinmandi.core.feature_coindetail.domain.usecases

import com.example.coinmandi.core.feature_coindetail.domain.model.ChartData
import com.example.coinmandi.core.feature_coindetail.states.ChartRange
import com.example.coinmandi.core.sensitive.GekoRoutes
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoError
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoResult
import com.example.coinmandi.feature_explore.data.repositories.ExploreRepo

class GetCoinChartData(val repo: ExploreRepo) {
    suspend operator fun invoke(coinid : String , day : ChartRange) : GekoResult<ChartData , GekoError>{
        val interval =  if(day.range > 1){
            "interval" to "daily"
        } else "interval" to "daily"

        return repo.GetChartData(
            endpoint = GekoRoutes.CoinDetailEp + coinid + "/market_chart",
            params = mapOf(
                "vs_currency" to "usd" ,
                "days" to day.range,
                interval
            ),
            headers = null
        )
    }
}