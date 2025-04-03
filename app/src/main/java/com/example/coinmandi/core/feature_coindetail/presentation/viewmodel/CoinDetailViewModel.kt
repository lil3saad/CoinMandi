package com.example.coinmandi.core.feature_coindetail.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinmandi.core.feature_coindetail.domain.model.ChartData
import com.example.coinmandi.core.feature_coindetail.domain.model.CoinMarketDetail
import com.example.coinmandi.core.feature_coindetail.domain.usecases.GetCoinChartData
import com.example.coinmandi.core.feature_coindetail.domain.usecases.GetCoinDetailsUc
import com.example.coinmandi.core.feature_coindetail.states.CoinDetailEvents
import com.example.coinmandi.core.feature_coindetail.states.CoinDetailPageState
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoErrorWithCodeMessage
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CoinDetailViewModel(val getdetailsuc : GetCoinDetailsUc ,
    val getchartUc : GetCoinChartData)
    : ViewModel() {

    private var _PageState : MutableStateFlow<CoinDetailPageState> = MutableStateFlow(
        CoinDetailPageState()
    )
    val pagestate = _PageState.asStateFlow()

    fun onEvent(event : CoinDetailEvents){
        when(event){
            is CoinDetailEvents.FetchCoin -> {
                viewModelScope.launch {
                    val result = getdetailsuc( event.coinid )
                    when(result){
                        is GekoResult.Failed<*> ->{
                            when(val error = result.Error){
                                is GekoErrorWithCodeMessage -> {
                                    _PageState.value = _PageState.value.copy(
                                        coindata_Error = error.message
                                    )
                                }
                                else -> Unit
                            }
                        }
                        is GekoResult.Success<CoinMarketDetail> -> {
                            val coindata = result.Data
                            if(coindata!=null){
                                _PageState.value = _PageState.value.copy(
                                    coindata = result.Data
                                )
                            }
                            else {
                                _PageState.value = _PageState.value.copy(
                                    coindata_Error = "No Market Data available found for this coin"
                                )
                            }
                        }
                    }
                }
            }
            is CoinDetailEvents.ChangeMarketOption-> {
                _PageState.value = _PageState.value.copy(
                    SelectedData = event.option
                )
            }
            is CoinDetailEvents.ChangeHighLowTime -> {
               _PageState.value = _PageState.value.copy(
                   timeline = event.time
               )
            }

            is CoinDetailEvents.FetchChartData -> {
                _PageState.value = _PageState.value.copy(
                    ChartLoading = true,
                    SelectedChartTime = event.range
                )
                viewModelScope.launch {
                    val result = getchartUc.invoke(
                        coinid = event.coinid,
                        day = event.range
                    )
                    when(result){
                        is GekoResult.Failed<*> -> {
                            // Show Error Message To User
                            when(val error = result.Error){
                                is GekoErrorWithCodeMessage -> {
                                    _PageState.value = _PageState.value.copy(
                                        ChartLoading = false,
                                        ChartDataError = error.message
                                    )
                                }
                                else -> Unit
                            }
                        }
                        is GekoResult.Success<ChartData> -> {
                            val chartdata = result.Data
                            if(chartdata != null){
                                val prices = chartdata.prices
                                if(prices != null){
                                    // Update Page Prices List in Page State
                                    _PageState.value = _PageState.value.copy(
                                        ChartLoading = false,
                                        ChartData = chartdata
                                    )
                                }else{
                                    _PageState.value = _PageState.value.copy(
                                        ChartLoading = false,
                                        ChartDataError = "Coin Chart Data Not Found"
                                    )

                                }
                            }else {
                                _PageState.value = _PageState.value.copy(
                                    ChartLoading = false,
                                    ChartDataError = "Coin Chart Data Not Found"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}