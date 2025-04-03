package com.example.coinmandi.feature_explore.presentation.searchpage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.coinmandi.feature_explore.presentation.viewmodels.ExploreViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.coinmandi.feature_explore.presentation.explorePage.components.SearchCoinCard
import com.example.coinmandi.feature_explore.presentation.searchpage.components.AllCoinsSearchBar
import com.example.coinmandi.ui.theme.BrandColor
import com.example.coinmandi.ui.theme.Typography
import org.koin.compose.viewmodel.koinViewModel
import com.example.coinmandi.R


@Composable()
fun SearchPage(modifier: Modifier = Modifier,
               featurevm : ExploreViewModel = koinViewModel<ExploreViewModel>()
){
    val pagestate by featurevm.PageState
    val searchResult by featurevm.searchResult.collectAsState()
    val coinlist = searchResult.SearchResultList
    Column(modifier = modifier.fillMaxSize() , horizontalAlignment = Alignment.CenterHorizontally) {
        AllCoinsSearchBar(searchText = searchResult.SearchText,
            onSearchTextChange = { usertext ->
                // Update Page State in Vm
                featurevm.ObserveSearchText()
                featurevm.updateSearchText(usertext)
            },
            modifier = Modifier.fillMaxWidth(0.9f),
            performSearch = { usertext ->
                // Search On Click
                featurevm.Searchcoin(usertext)
            }
        )
        if(coinlist != null){
            LazyColumn(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                items(items = coinlist){ coin ->
                    SearchCoinCard(coin = coin , modifier = Modifier.fillMaxWidth())
                }
            }
        }else if(searchResult.Searching) {
            Box(modifier = Modifier.fillMaxWidth(0.8f).fillMaxHeight(0.8f) , contentAlignment = Alignment.Center){
                CircularProgressIndicator( modifier = Modifier.size(50.dp),
                    color = BrandColor
                )
            }
        }else if(searchResult.EnterProperLenght){
            Box(modifier = Modifier.fillMaxWidth(0.8f).fillMaxHeight(0.8f) , contentAlignment = Alignment.TopCenter){
                Image(imageVector = ImageVector.vectorResource(R.drawable.retry) , contentDescription = "No Data Found" ,
                    modifier = Modifier
                        .size(300.dp)
                        .alpha(0.5f)
                )
                Text("Enter atleast 3 characters" , style = Typography.titleSmall , modifier = Modifier.padding(top = 260.dp))
            }
        }
        else if(searchResult.NoCoinsFound) {
            Box(modifier = Modifier.fillMaxWidth(0.8f).fillMaxHeight(0.8f) , contentAlignment = Alignment.TopCenter){
                Image( imageVector = ImageVector.vectorResource(R.drawable.searchingillu) , contentDescription = "No Data Found" ,
                    modifier = Modifier.size(300.dp)
                        .alpha(0.2f)
                )
                Text("No Coins Found" , style = Typography.titleSmall , modifier = Modifier.padding(top = 250.dp))
            }
        }

    }


}