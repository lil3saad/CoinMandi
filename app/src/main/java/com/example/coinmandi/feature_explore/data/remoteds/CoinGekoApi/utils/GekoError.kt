package com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils

sealed interface GekoError {
    enum class RemoteError : GekoError {
         REDIRECTION_ERROR,
         CLIENT_ERROR,
         SERVER_ERROR,
         INTERNET_ERROR,
         CONNECTION_TIMEOUT ,
         UNKNOWN
     }
    enum class LocalError : GekoError {
        INTERNET_ERROR,
        SERIALIZATION_ERROR ,
        CONNECTION_TIMEOUT ,
        UNKNOWN
    }
}

data class GekoErrorWithCodeMessage(
   val error : GekoError ,
   val code : Int? ,
   val message : String?
) : GekoError