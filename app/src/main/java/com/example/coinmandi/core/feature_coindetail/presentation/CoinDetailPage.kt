package com.example.coinmandi.core.feature_coindetail.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.coinmandi.core.feature_coindetail.domain.model.CoinMarketDetail
import com.example.coinmandi.core.feature_coindetail.presentation.components.CoinDetailHeader
import com.example.coinmandi.core.feature_coindetail.presentation.components.GraphicalData
import com.example.coinmandi.core.feature_coindetail.presentation.components.MarketData
import com.example.coinmandi.core.feature_coindetail.presentation.viewmodel.CoinDetailViewModel
import com.example.coinmandi.core.feature_coindetail.states.ChartRange
import com.example.coinmandi.core.feature_coindetail.states.CoinDetailEvents
import com.example.coinmandi.core.feature_coindetail.states.CoinDetailPageState
import com.example.coinmandi.ui.theme.AppBg
import com.example.coinmandi.ui.theme.Typography
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CoinDetailPage(modifier: Modifier,
    coinId : String,
    featurevm : CoinDetailViewModel = koinViewModel<CoinDetailViewModel>()
){
    val pagestate by featurevm.pagestate.collectAsState()
    LaunchedEffect(Unit) {
        featurevm.onEvent(CoinDetailEvents.FetchCoin(coinId))
        featurevm.onEvent(CoinDetailEvents.FetchChartData(coinid = coinId, range = pagestate.SelectedChartTime))
    }
    Column(modifier.fillMaxSize().background(AppBg),
        horizontalAlignment = Alignment.CenterHorizontally) {
        val coin  = pagestate.coindata
        if(coin !=null){
            Log.d("DETAILS", "NEW COIN DATE FETCHED and PageState Reacting")
            CoinDetailContent(modifier = Modifier.fillMaxWidth(),
                coin = coin,
                pageState = pagestate,
                featurevm = featurevm
            )
        }
        val error = pagestate.coindata_Error
        if(error !=null){
            Text(text = error , style = Typography.titleLarge)
        }
    }
}
@Composable
fun CoinDetailContent(modifier : Modifier,
    coin : CoinMarketDetail,
    featurevm: CoinDetailViewModel,
    pageState : CoinDetailPageState
){
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {

        CoinDetailHeader(modifier = modifier.fillMaxWidth() , coin)

        GraphicalData(modifier = modifier.fillMaxWidth()
            .fillMaxHeight(),
            pageState,
            featurevm
        )

        MarketData(modifier = modifier.fillMaxWidth()
            .background(color = Color.Transparent),
            coin = coin,
            pageState = pageState,
            featurevm = featurevm
        )
    }
}

