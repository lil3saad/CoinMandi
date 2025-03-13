package com.example.coinmandi.feature_explore.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoResult
import com.example.coinmandi.feature_explore.domain.model.GekoCoin
import com.example.coinmandi.feature_explore.domain.model.TrendingCoins
import com.example.coinmandi.feature_explore.domain.usecases.GetCoinsUC
import com.example.coinmandi.feature_explore.domain.usecases.GetTrendingListUC
import com.example.coinmandi.feature_explore.presentation.states.ExplorePageState
import com.example.coinmandi.feature_explore.presentation.states.SelectedCategoryState
import kotlinx.coroutines.launch
import kotlin.collections.isNotEmpty

class ExploreViewModel(
    private val getCoins : GetCoinsUC,
    private val getTrending : GetTrendingListUC
) : ViewModel() {

    private val _PageState : MutableState<ExplorePageState> = mutableStateOf(ExplorePageState())
    val PageState : State<ExplorePageState> = _PageState

    fun GetCategoryCoins(category : SelectedCategoryState) = viewModelScope.launch {
        Log.d("GEKO" , "Getting Rwa Coins from CoinGeko")
        _PageState.value = _PageState.value.copy( CategoryListLoading = true )
        val Gekoresult = getCoins.invoke(category = category.categoryid)
        when(Gekoresult){
            is GekoResult.Failed<*> -> {
                Log.d("GEKO" , "${Gekoresult.Error}")
            }
            is GekoResult.Success<List<GekoCoin>> -> {
                     val body = Gekoresult.Data
                     if(body !=null && body.isNotEmpty()){
                         _PageState.value = _PageState.value.copy(
                             SelectedCategory = category,
                             CategoryList = body,
                             CategoryListLoading = false
                         )
                     }else {
                         Log.d("GEKO" , "No Real World Coins Available in the Market")
                     }
            }
        }
    }
    fun GetTrendingList() = viewModelScope.launch {
        Log.d("GEKO" , "Getting TrendingList")
        _PageState.value = _PageState.value.copy(
            TrendingListLoading = true
        )
        val gekoresult = getTrending.invoke()
        when(gekoresult){
            is GekoResult.Failed<*> -> {
                Log.d("GEKO" , "${gekoresult.Error}")
            }
            is GekoResult.Success<TrendingCoins> -> {
                val body = gekoresult.Data
                if(  body != null ){
                    Log.d("GEKO" , "Trending Coins Class Received and Updated")
                       _PageState.value = _PageState.value.copy(
                           TrendingListLoading = false,
                           TrendingList = body
                       )
                }else {
                    Log.d("GEKO" , "No Real World Coins Available in the Market")
                }
            }
        }
    }
    fun toogleCategoryMenu( toggle : Boolean ) {
        _PageState.value = _PageState.value.copy(
            IsCateogoryMenuVisible = toggle
        )
    }

}