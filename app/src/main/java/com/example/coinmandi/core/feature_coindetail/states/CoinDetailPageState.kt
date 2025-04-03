package com.example.coinmandi.core.feature_coindetail.states

import com.example.coinmandi.core.feature_coindetail.domain.model.ChartData
import com.example.coinmandi.core.feature_coindetail.domain.model.CoinMarketDetail

data class CoinDetailPageState(
    val coindataLoading : Boolean = false,
    val coindata : CoinMarketDetail? = null,
    val coindata_Error : String? = null,
    val SelectedData : MarketOptions = MarketOptions.Overview,
    val timeline : HighLowTime = HighLowTime.Twenty4,
    val SelectedChartTime : ChartRange = ChartRange.ThirtyDay,
    val ChartLoading : Boolean = false,
    val ChartData : ChartData = ChartData(prices = listOf(
        listOf(
            10.0,
            11.0
        ) ,
        listOf(
            10.0,
            11.0
        )
    )),
    val ChartDataError : String? = null
)
enum class MarketOptions(val value : String) {
    Overview("Overview") ,
    About("About")
}
enum class HighLowTime(val type : String){
    Twenty4(type = "24h"),
    AllTime(type = "All")
}
enum class ChartRange(val range : Int, val label : String){
    OneDay(range = 1 , label = "1D"),
    OneWeek(range = 7 , label = "1W"),
    ThirtyDay(range = 30, label = "30D"),
    NintyDay(range = 90, label = "90D"),
    OneYear(range = 365 , label = "1Y")
}