package com.example.coinmandi.feature_home.domain.model.restutils

sealed class RestResult<D , E : RestError>(
  val Data : D? = null,
  val Error : E? = null
){
   data class Success<D>(val passdata: D? = null) : RestResult<D,RestError>(passdata)
   data class UnSuccessful<D>(val passerror: RestError? = null) : RestResult<D , RestError>(Error = passerror)
}
