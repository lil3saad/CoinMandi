package com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi

import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoError
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoErrorWithCodeMessage
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

// This Could be Service Implementation
class GekoService(
     val  client : HttpClient
) {
    suspend inline fun <reified D>  Get(
        endpoint : String,
        params : Map<String, Any>?,
        headers : Map<String , Any>?
    ) : GekoResult<D, GekoError> {
        return SafeCall {
            client.get {
                     url(endpoint)
                     params?.forEach { key , value ->
                         parameter(key =  key , value = value)
                     }
                     headers?.forEach { key, value ->
                         header(key = key , value = value)
                     }
            }
        }
    }

    suspend inline fun <reified D> SafeCall(
        execute : () -> HttpResponse
    )  : GekoResult<D, GekoError> {
        val respone = try {
                execute()
        }catch(e : UnresolvedAddressException){
            e.printStackTrace()
            return GekoResult.Failed(
                error = GekoErrorWithCodeMessage(
                    code = null,
                    error = GekoError.LocalError.INTERNET_ERROR,
                    message = "No Internet Found"
                )
            )
        } catch(e : SerializationException){
            e.printStackTrace()
            return GekoResult.Failed(
                error = GekoErrorWithCodeMessage(
                    code = null,
                    error = GekoError.LocalError.SERIALIZATION_ERROR,
                    message = "No Internet Found"
                )
            )
        } catch (e : Exception){
            e.printStackTrace()
            return GekoResult.Failed(
                error = GekoErrorWithCodeMessage(
                    code = null,
                    error = GekoError.LocalError.UNKNOWN,
                    message = "Error Fetching From Data"
                )
            )
        }
        return ResponeToResult(respone)
    }

   suspend inline fun <reified D> ResponeToResult(response: HttpResponse): GekoResult<D, GekoError> =
       when(response.status.value){
           in 200..299 -> {
                    GekoResult.Success(
                        // Get ListType
                        data = response.body<D>()
                    )
           }
           in 300 .. 399 -> {
               GekoResult.Failed(
                   error = GekoErrorWithCodeMessage(
                       code = response.status.value,
                       error = GekoError.RemoteError.REDIRECTION_ERROR,
                       message = "Redirection Error Please Try again"
                   )
               )
           }
           in 400 .. 499 -> {
               GekoResult.Failed(
                   error = GekoErrorWithCodeMessage(
                       code = response.status.value,
                       error = GekoError.RemoteError.CLIENT_ERROR,
                       message = "Error Please Try Again"
                   )
               )
           }
           in 500..599 -> {
               GekoResult.Failed(
                   error = GekoErrorWithCodeMessage(
                       code = response.status.value,
                       error = GekoError.RemoteError.SERVER_ERROR,
                       message = "Server Under Maintenance"
                   )
               )
           }
           else -> {
               GekoResult.Failed(
                   error = GekoErrorWithCodeMessage(
                       code = null,
                       error = GekoError.RemoteError.UNKNOWN,
                       message = "Error Fetching From DataBase"
                   )
               )
           }
       }

}