package com.example.coinmandi.core.feature_coindetail.states

sealed class CoinDetailEvents{
    data class FetchCoin(val coinid : String) : CoinDetailEvents()
    data class ChangeMarketOption(val option : MarketOptions) : CoinDetailEvents()
    data class ChangeHighLowTime(val time : HighLowTime) : CoinDetailEvents()
    data class FetchChartData(val coinid : String ,val range : ChartRange)  : CoinDetailEvents()
}