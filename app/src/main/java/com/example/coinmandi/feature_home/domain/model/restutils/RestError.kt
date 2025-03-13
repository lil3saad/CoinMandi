package com.example.coinmandi.feature_home.domain.model.restutils

sealed class RestError {
    enum class NetworkError  {
        REDIRECTION_ERROR ,
        CLIENT_ERROR,
        SERVER_ERROR,
        NO_INTERNET ,
        CONNECTION_TIMEOUT,
        UNKNOWN,
    }
    enum class LocalError {
        NO_INTERNET ,
        CONNECTION_TIMEOUT,
        SERIALIZATION_ERROR,
        NOT_FOUND,
        UNKNOWN,
    }
}
data class RestErrorWithCode(
    val error : RestError,
    val code : Int
) : RestError()