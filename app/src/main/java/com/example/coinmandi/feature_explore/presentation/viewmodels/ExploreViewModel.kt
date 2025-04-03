package com.example.coinmandi.feature_explore.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoError
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoErrorWithCodeMessage
import com.example.coinmandi.feature_explore.data.remoteds.CoinGekoApi.utils.GekoResult
import com.example.coinmandi.feature_explore.domain.model.GekoCoin
import com.example.coinmandi.feature_explore.domain.model.GekoSearch
import com.example.coinmandi.feature_explore.domain.model.TrendingCoins
import com.example.coinmandi.feature_explore.domain.usecases.GetCoinsUC
import com.example.coinmandi.feature_explore.domain.usecases.GetTrendingListUC
import com.example.coinmandi.feature_explore.domain.usecases.SearchCoinUC
import com.example.coinmandi.feature_explore.presentation.states.CoinSearchState
import com.example.coinmandi.feature_explore.presentation.states.ExplorePageState
import com.example.coinmandi.feature_explore.presentation.states.SelectedCategoryState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.collections.isNotEmpty

class ExploreViewModel(
    private val getCoins : GetCoinsUC,
    private val getTrending : GetTrendingListUC,
    private val searchCoin : SearchCoinUC
) : ViewModel() {

    private val _PageState : MutableState<ExplorePageState> = mutableStateOf(ExplorePageState(
        TrendingListLoading = true,
        CategoryListLoading = true
    ))
    val PageState : State<ExplorePageState> = _PageState

    fun GetCategoryCoins(category : SelectedCategoryState) = viewModelScope.launch {
        Log.d("GEKO" , "Getting Rwa Coins from CoinGeko")
        _PageState.value = _PageState.value.copy( CategoryListLoading = true )
        val gekoresult = getCoins.invoke(category = category.categoryid)
        when(gekoresult){
            is GekoResult.Failed<*> -> {
                Log.d("GEKO" , "${gekoresult.Error}")
                when(val error = gekoresult.Error){
                    is GekoErrorWithCodeMessage -> {
                        _PageState.value = _PageState.value.copy(
                            CategoryListLoading = false,
                            CategoryListMessage = "${error.message}"
                        )
                    }
                    else -> Unit
                }
            }
            is GekoResult.Success<List<GekoCoin>> -> {
                     val body = gekoresult.Data
                     if(body !=null && body.isNotEmpty()){
                         _PageState.value = _PageState.value.copy(
                             SelectedCategory = category,
                             CategoryList = body,
                             CategoryListLoading = false
                         )
                     }else {
                         _PageState.value = _PageState.value.copy(
                             CategoryListLoading = false,
                             CategoryListMessage = "No ${category.catergoryname} coins Available in the Market"
                         )
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
                when(val error = gekoresult.Error){
                    is GekoErrorWithCodeMessage -> {
                        _PageState.value = _PageState.value.copy(
                            TrendingListLoading = false,
                            TrendingListMessage = "${error.message}"
                        )
                    }
                    else -> Unit
                }
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
                    Log.d("GEKO" , "No Updates in the Market ")
                }
            }
        }
    }
    fun toogleCategoryMenu( toggle : Boolean ) {
        _PageState.value = _PageState.value.copy(
            IsCateogoryMenuVisible = toggle
        )
    }

    private val _searchResult : MutableStateFlow<CoinSearchState> = MutableStateFlow(CoinSearchState())
    val searchResult : StateFlow<CoinSearchState> = _searchResult.asStateFlow()

    fun updateSearchText(usertext : String) = viewModelScope.launch {
        _searchResult.emit(CoinSearchState(SearchText = usertext))
    }
    private var previousString = ""
    @OptIn(FlowPreview::class)
    fun ObserveSearchText() = viewModelScope.launch {
        searchResult
            .map { value -> value.SearchText }
            .distinctUntilChanged()
            .debounce(500L)
            .collectLatest { searchQuery ->
                if(searchQuery == previousString){ }
                else {
                    Log.d("GEKO" , "Reacting from ObserveSeaachText")
                    if(searchQuery.length < 3 || searchQuery.isBlank() )
                        _searchResult.value = _searchResult.value.copy(
                            EnterProperLenght = true
                        )
                    else {
                        previousString = searchQuery
                        Searchcoin(searchQuery)
                    }
                }

            }
    }
    fun Searchcoin(query : String)  = viewModelScope.launch {
        Log.d("GEKO" , "Searching Coin From Api")
        _searchResult.value = _searchResult.value.copy(
            Searching = true ,
            EnterProperLenght = false
            )
        val gekoresult =  searchCoin(query)
        when(gekoresult) {
          is GekoResult.Failed<*> -> {
              _searchResult.value = _searchResult.value.copy(
                  NoCoinsFound = true
              )
            Log.d("GEKO" , "${gekoresult.Error}")
          }
          is GekoResult.Success<GekoSearch> -> {
            val body = gekoresult.Data
            if(body != null && body.coins != null && body.coins.isNotEmpty()){
                Log.d("GEKO" , "${body.coins.toString()} Coins obtained")
                _searchResult.value = _searchResult.value.copy(
                    Searching = false,
                    NoCoinsFound = false,
                    SearchResultList = body.coins
                )
            }else {
                _searchResult.value = _searchResult.value.copy(
                    Searching = false,
                    NoCoinsFound = true
                )
            }
          }
        }
    }

}