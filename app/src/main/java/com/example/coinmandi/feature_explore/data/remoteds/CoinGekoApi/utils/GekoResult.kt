package com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils

sealed class GekoResult<D , E : GekoError>(
    val Data : D? = null ,
    val Error : E? = null
) {
    class Success<D>(data : D?) : GekoResult<D, GekoError>(Data = data)
    class Failed<D>( error : GekoError) : GekoResult<D , GekoError>(Error = error)
}