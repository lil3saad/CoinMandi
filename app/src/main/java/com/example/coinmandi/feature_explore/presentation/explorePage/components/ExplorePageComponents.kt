package com.example.coinmandi.feature_explore.presentation.explorePage.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.coinmandi.feature_explore.domain.model.Coin
import com.example.coinmandi.feature_explore.domain.model.GekoCoin
import com.example.coinmandi.feature_explore.domain.model.Item
import com.example.coinmandi.feature_explore.domain.model.SearchCoin
import com.example.coinmandi.feature_explore.presentation.states.ExplorePageState
import com.example.coinmandi.feature_explore.presentation.states.SelectedCategoryState
import com.example.coinmandi.feature_explore.presentation.viewmodels.ExploreViewModel
import com.example.coinmandi.ui.theme.AppBg
import com.example.coinmandi.ui.theme.BodyFont
import com.example.coinmandi.ui.theme.BrandColor
import com.example.coinmandi.ui.theme.HeadingFont
import kotlinx.coroutines.launch
import kotlin.collections.chunked
import kotlin.collections.forEach



@Composable
fun TrendingCoins(modifier: Modifier = Modifier,
    pagestate : ExplorePageState ){

    Box(modifier = Modifier.fillMaxWidth().padding(top = 5.dp)
        .fillMaxWidth().fillMaxHeight(0.35f) , contentAlignment = Alignment.Center) {
        if(pagestate.TrendingListLoading){
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
                color = BrandColor
            )
        }else {
            if((pagestate.TrendingList != null) && (pagestate.TrendingList.coins != null) && pagestate.TrendingList.coins.isNotEmpty() ){
                Column(modifier = Modifier.fillMaxSize()) {
                    val trendingcoins : List<Coin> = pagestate.TrendingList.coins
                    val scrollState = rememberLazyListState()
                    val centerfling = rememberSnapFlingBehavior( scrollState , snapPosition = SnapPosition.Start)
                    val coroutinescope = rememberCoroutineScope()
                    LazyRow(modifier = modifier.fillMaxWidth()
                        .fillMaxHeight(0.85f),
                        state = scrollState,
                        flingBehavior = centerfling,
                    ){
                        itemsIndexed(items = trendingcoins,
                            key = { index, coin -> coin.item.coin_id }
                        ) { index, coin ->  //
                            if(  coin.item.data!=null && coin.item.data.content!= null)
                                Box(modifier = Modifier.fillParentMaxWidth(),
                                    contentAlignment = Alignment.Center) {
                                    NewsCard(
                                        modifier = Modifier.fillMaxWidth(),
                                        trendingcoin = coin.item
                                    )
                                }
                        }
                    }
                    Row(modifier = Modifier.padding(bottom = 10.dp)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        trendingcoins.forEachIndexed{ index , coin ->
                            if(  coin.item.data!=null && coin.item.data.content!= null)
                                Box(modifier = Modifier
                                    .padding(4.dp)
                                    .size(5.dp)
                                    .background(shape = CircleShape, color = if(scrollState.firstVisibleItemIndex == index){
                                        BrandColor
                                    } else Color.White
                                    )
                                    .clickable {
                                        coroutinescope.launch {
                                            scrollState.animateScrollToItem(index)
                                        }

                                    }
                                )
                        }
                    }
                }

            }
        }
    }


}

@Composable
fun NewsCard(modifier: Modifier = Modifier,
             trendingcoin : Item
){
    Row(modifier = modifier.padding(15.dp)
        .fillMaxWidth()
        .background(AppBg)
        .border(1.dp ,color = Color.LightGray.copy(0.1f), shape = RoundedCornerShape(20.dp))
    ){
        Column(modifier = Modifier.fillMaxSize().padding(20.dp) ,
            verticalArrangement = Arrangement.SpaceBetween
            ) { // TopRow

            Row(verticalAlignment = Alignment.CenterVertically ,
                horizontalArrangement = Arrangement.SpaceBetween ,
                modifier = Modifier.fillMaxWidth()){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = trendingcoin.thumb,
                        contentDescription = "coin image",
                        modifier = Modifier.padding(end = 12.dp)
                            .size(55.dp)
                    )
                    Column(verticalArrangement = Arrangement.Center) {
                        Text(text ="${trendingcoin.symbol}", style = TextStyle(color = Color.White , fontSize = 20.sp, fontFamily = HeadingFont , fontWeight = FontWeight.Bold ),
                            modifier = Modifier.padding(bottom = 7.dp)
                        )
                        Text(text = "${trendingcoin.name}", style = TextStyle(color = Color.Gray , fontSize = 16.sp , fontFamily = BodyFont ) ,
                            modifier = Modifier.width(100.dp)
                        )
                    }
                }
                Row {
                    Text("$%.4f".format(trendingcoin.data?.price) , style = TextStyle( color = Color.White,
                        fontSize = 20.sp , fontFamily = HeadingFont , fontWeight = FontWeight.Bold),
                        modifier = Modifier.width(200.dp),
                        textAlign = TextAlign.End,
                        softWrap = false,
                    )
                }
            }
            Column {
                Text(text = "${trendingcoin.data?.content?.title}" ,
                    style = TextStyle( fontSize = 18.sp , color = Color.White , fontFamily = BodyFont),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 7.dp),
                    overflow = TextOverflow.Ellipsis ,
                    softWrap = true,
                    maxLines = 1
                )
                Text(text = "${trendingcoin.data?.content?.description}" ,
                    style = TextStyle( fontSize = 14.sp , color = Color.White , fontFamily = BodyFont),
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis ,
                    maxLines = 4,
                    softWrap = true
                )
            }
        }

    }
}
@Composable
fun CategoryMenu(modifier: Modifier = Modifier,
    pageViewModel: ExploreViewModel,
    pagestate: ExplorePageState){
    Column(modifier = modifier.padding( horizontal = 10.dp)){
        SelectedCategoryState.entries.chunked(2).forEach() { pair ->
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                pair.forEach { entry ->
                    CategoryItem(
                        entry = entry,
                        pagestate = pagestate,
                        pageViewModel = pageViewModel,
                        modifier = Modifier.weight(1f)
                    )
                }
                // Add an empty space if there's only one item in the pair to balance the row
                if (pair.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
@Composable
fun CategoryItem(entry : SelectedCategoryState,
                 pagestate: ExplorePageState,
                 pageViewModel: ExploreViewModel,
                 modifier: Modifier){
    Row( modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected =   pagestate.SelectedCategory == entry  , onClick = { pageViewModel.GetCategoryCoins(entry)
            pageViewModel.toogleCategoryMenu(toggle = false)
                                                                                       },
            colors = RadioButtonDefaults.colors(
                selectedColor = BrandColor,
                unselectedColor = Color.White
            )
        )
        Text(text = entry.catergoryname , style =  TextStyle(
            color = if(entry == pagestate.SelectedCategory ) BrandColor
            else Color.White,
            fontSize = 15.sp , fontFamily = BodyFont, fontWeight = FontWeight.Medium),
            modifier = Modifier.clickable {
                pageViewModel.GetCategoryCoins(entry)
                pageViewModel.toogleCategoryMenu(toggle = false)
            }
        )

    }
}
@Composable
fun CategoryListBox(modifier: Modifier,
                    state : ExplorePageState){
    Box(modifier = modifier,
        contentAlignment = Alignment.Center ){
        if( (state.CategoryList != null) && state.CategoryList.isNotEmpty() ){
            if(!state.CategoryListLoading) {
                LazyColumn {
                    items(state.CategoryList) { coin ->
                        ExploreCoinCard(
                            coin = coin,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }else {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = BrandColor
                )
            }
        }else {
            Text("No Coins Found in this category" , style = TextStyle(fontSize = 18.sp , color = Color.White , fontFamily = HeadingFont , fontWeight = FontWeight.Bold))
        }
    }
}

@Composable
fun ExploreCoinCard(coin : GekoCoin? = GekoCoin(
    symbol = "BTC",
    currentPrice = 10.0035,
    priceChangePercentage24h = -2.0
), modifier: Modifier = Modifier ){
        coin?.priceChangePercentage24h?.let { it ->
            val Redcolorstops = arrayOf(
                0.02f to Color.Red.copy(alpha = 0.05f),
                0.2f to Color.Transparent
            )
            val RedBrush = Brush.horizontalGradient(
                colorStops = Redcolorstops,
                startX = Float.POSITIVE_INFINITY,
                endX = 0f
            )
            val Greencolorstops = arrayOf(
                0.02f to Color.Green.copy(alpha = 0.05f),
                0.2f to Color.Transparent
            )
            val GreenBrush = Brush.horizontalGradient(
                colorStops = Greencolorstops,
                startX = Float.POSITIVE_INFINITY,
                endX = 0f
            )
            Row(modifier = modifier .padding(horizontal = 10.dp , vertical = 4.dp)
                .fillMaxWidth().height(IntrinsicSize.Min)
                .border(1.dp , color = Color.LightGray.copy(alpha = 0.1f) , shape = RoundedCornerShape(20.dp))
                .background(Color.Transparent, shape = RoundedCornerShape(20.dp))
                .background(brush = if(it < 0) RedBrush
                else GreenBrush , shape = RoundedCornerShape(20.dp))
            ){
                Row(modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 18.dp , horizontal = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(modifier = Modifier , verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = coin?.image,
                            contentDescription = "Btn Symbol",
                            modifier = Modifier.padding(end = 12.dp)
                                .size(35.dp)
                        )
                        Column(verticalArrangement = Arrangement.Center) {
                            Text(text = "${coin?.symbol}", style = TextStyle(color = Color.White , fontSize = 20.sp, fontFamily = HeadingFont , fontWeight = FontWeight.Bold ),
                                modifier = Modifier.padding(bottom = 7.dp)
                            )
                            Text(text = "${coin?.name}", style = TextStyle(color = Color.Gray , fontSize = 14.sp , fontFamily = BodyFont ) ,
                                modifier = Modifier.width(150.dp)
                            )
                        }
                    }
                    Column(modifier = Modifier,
                        verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.End) {
                        Text(text = "$${coin.currentPrice}" , style = TextStyle(color = Color.White , fontSize = 20.sp, fontFamily = HeadingFont , fontWeight = FontWeight.Medium),
                            modifier = Modifier.padding(bottom = 7.dp)
                        )
                        val number = it
                        Text(text = "${String.format("%.2f", number).toDouble()}%",
                            style = TextStyle(color = if(it < 0 ) Color.Red
                            else Color.Green, fontSize = 15.sp , fontWeight = FontWeight.Bold , fontFamily = BodyFont)
                        )
                    }
                }
            }
    }
}
@Composable
fun SearchCoinCard(coin : SearchCoin
, modifier: Modifier = Modifier ){
    Row(modifier = modifier.padding(horizontal = 10.dp , vertical = 4.dp)
        .height(IntrinsicSize.Min)
        .border(1.dp , color = Color.LightGray.copy(alpha = 0.1f) , shape = RoundedCornerShape(20.dp))
        .background(Color.Transparent, shape = RoundedCornerShape(20.dp))
    ){
        Row(modifier = Modifier.fillMaxWidth()
            .padding(vertical = 18.dp , horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            ) {
            Row(modifier = Modifier , verticalAlignment = Alignment.CenterVertically) {
                if(coin.large !=null )
                    AsyncImage(model = coin.large,
                        contentDescription = "Btn Symbol",
                        modifier = Modifier.padding(end = 12.dp)
                            .size(35.dp)
                    )
                if(coin.name != null && coin.symbol != null)
                    Column(verticalArrangement = Arrangement.Center) {
                        Text(text = coin.symbol, style = TextStyle(color = Color.White , fontSize = 20.sp, fontFamily = HeadingFont , fontWeight = FontWeight.Bold ),
                            modifier = Modifier.padding(bottom = 7.dp)
                        )
                        Text(text = coin.name, style = TextStyle(color = Color.Gray , fontSize = 14.sp , fontFamily = BodyFont ) ,
                            modifier = Modifier.width(150.dp)
                        )
                    }
            }
        }
    }
}