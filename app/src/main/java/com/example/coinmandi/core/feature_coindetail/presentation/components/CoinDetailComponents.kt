package com.example.coinmandi.core.feature_coindetail.presentation.components

import android.content.Intent
import android.text.Layout
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.example.coinmandi.R
import com.example.coinmandi.core.feature_coindetail.domain.model.ChartData
import com.example.coinmandi.core.feature_coindetail.domain.model.CoinMarketDetail
import com.example.coinmandi.core.feature_coindetail.presentation.viewmodel.CoinDetailViewModel
import com.example.coinmandi.core.feature_coindetail.states.ChartRange
import com.example.coinmandi.core.feature_coindetail.states.CoinDetailEvents
import com.example.coinmandi.core.feature_coindetail.states.CoinDetailPageState
import com.example.coinmandi.core.feature_coindetail.states.HighLowTime
import com.example.coinmandi.core.feature_coindetail.states.MarketOptions
import com.example.coinmandi.ui.theme.AppBg
import com.example.coinmandi.ui.theme.BrandColor
import com.example.coinmandi.ui.theme.Typography
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.component.shadow
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.insets
import com.patrykandpatrick.vico.compose.common.shape.markerCorneredShape
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.CartesianMarker
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.LayeredComponent
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shader.ShaderProvider
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import java.text.DecimalFormat


@Composable
fun CoinDetailHeader(modifier: Modifier,
                     coin : CoinMarketDetail){
    // CoinDetail Header
    Row(modifier = modifier
        .padding(horizontal = 15.dp , vertical = 12.dp) , horizontalArrangement = Arrangement.SpaceBetween ,
        verticalAlignment = Alignment.CenterVertically){
        Row {
            AsyncImage(model = coin.image.large,
                contentDescription = "Coin Image",
                modifier = Modifier.size(55.dp)
                    .padding(end = 8.dp)
            )
            Column{
                Text(text = coin.symbol,
                    style = Typography.titleLarge)
                Text(text = coin.name,
                    style = Typography.bodySmall.copy(color = Color.Gray.copy(alpha = 0.7f)),
                    modifier = Modifier.fillMaxWidth(0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Column(verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.End) {
            Text(text = "$%.4f".format(coin.market_data.current_price.usd),
                style = Typography.titleSmall)
            coin.market_data.price_change_percentage_24h.let { price ->
                Text(text = "%.2f".format(price)+"%",
                    style = Typography.bodySmall.copy(
                        color = if(price < 0) Color.Red
                        else Color.Green
                    )
                )
            }
        }
    }
}

@Composable
fun GraphicalData(modifier: Modifier , pageState: CoinDetailPageState , viewModel: CoinDetailViewModel) {
    // Graphical Data
    // Make Sure this Composable Is Called if Chart Data Available
    Log.d("DETAILS" , "CHART DATA UPDATED" )
    JetpackComposeElectricCarSales(
        modifier,
        pageState.ChartData
    )
    // Time Range Row
    ChartTimeRow(
        modifier = Modifier.fillMaxWidth(),
        pageState = pageState,
        peform = { event ->
            viewModel.onEvent(event)
        }
    )
}
@Composable
private fun JetpackComposeElectricCarSales(modifier: Modifier = Modifier,
                                           chartData: ChartData
){

    val prices = chartData.prices!!

    val TimeList = mutableListOf<Double>()
    val PriceList = mutableListOf<Double>()

    var MaxPrice = Double.MIN_VALUE
    var MinPrice = Double.MAX_VALUE

    for(price in prices){
        val price = price!!

        val Xtime = price.get(0)!!
        val Yprice = price.get(1)!!

        if(Yprice > MaxPrice) MaxPrice = Yprice // Find Out the Max Price in the List

        if(Yprice < MinPrice) MinPrice = Yprice // Find Out the Min Price in the List

        TimeList.add(Xtime)
        PriceList.add(Yprice)
    }

     val RangeProvider = CartesianLayerRangeProvider.fixed(maxY = 100.9 ) // Max Of Prices List
     val YDecimalFormat = DecimalFormat("#.##$")
     val StartAxisValueFormatter = CartesianValueFormatter.decimal(YDecimalFormat)
     val MarkerValueFormatter = DefaultCartesianMarker.ValueFormatter.default(YDecimalFormat)

    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(prices) {
        var interval : Int = when {
            prices.size <= 8 -> 1
            prices.size <= 31 -> 5
            prices.size <= 91 -> 15
            prices.size <= 366 -> 60
            else -> maxOf(1, prices.size / 60) // Adaptive interval for large datasets
        }
        Log.d("DETAILS" , "$interval and ${prices.size}")

        val x = mutableListOf<Double>()
        val y = mutableListOf<Double>()
        for(index in 0 until TimeList.size step interval){
            x.add(TimeList[index])
        }
        for(index in 0 until PriceList.size   step interval ){
            y.add(PriceList[index])
        }

        modelProducer.runTransaction {
            // Learn more: https://patrykandpatrick.com/vmml6t.
            lineSeries {
                Log.d("DETAILS" , "${x.size == y.size} They are of Same Length ")
                Log.d("DETAILS" , "TimeList in Series $x")
                Log.d("DETAILS" , "PriceList in Series $y")
                val textx = listOf<Double>(
                    1.0,
                    2.0,
                    3.0,
                    4.0,
                    5.0,
                )
                val testy = listOf<Double>(
                    10.0,
                    20.0,
                    30.0,
                    40.0,
                    50.0,
                )
                series(textx, testy)
            }
        }
    }
    val lineColor = BrandColor
    CartesianChartHost(
        rememberCartesianChart(
            rememberLineCartesianLayer(
                lineProvider = LineCartesianLayer.LineProvider.series(
                    LineCartesianLayer.rememberLine(
                        fill = LineCartesianLayer.LineFill.single(fill(lineColor)),
                        areaFill = LineCartesianLayer.AreaFill.single(
                            fill(
                                ShaderProvider.verticalGradient(
                                    lineColor.copy(alpha = 0.4f).toArgb(),
                                    Color.Transparent.toArgb()
                                )
                            )
                        ),
                        )
                ),
                rangeProvider = RangeProvider,
            ),
            startAxis = VerticalAxis.rememberStart(valueFormatter = StartAxisValueFormatter),
            bottomAxis = HorizontalAxis.rememberBottom(),
            marker = rememberMarker(MarkerValueFormatter , showIndicator = true),
        ),
        modelProducer,
        modifier.height(500.dp),
        rememberVicoScrollState(scrollEnabled = true),
    )
}
@Composable
internal fun rememberMarker(valueFormatter: DefaultCartesianMarker.ValueFormatter =
        DefaultCartesianMarker.ValueFormatter.default(),
    showIndicator: Boolean = true,
): CartesianMarker {
    val labelBackgroundShape = markerCorneredShape(CorneredShape.Corner.Rounded)
    val labelBackground = rememberShapeComponent(
            fill = fill(MaterialTheme.colorScheme.background),
            shape = labelBackgroundShape,
            strokeThickness = 1.dp,
            strokeFill = fill(MaterialTheme.colorScheme.outline),
        )
    val label = rememberTextComponent(
            color = MaterialTheme.colorScheme.onSurface,
            textAlignment = Layout.Alignment.ALIGN_CENTER,
            padding = insets(8.dp, 4.dp),
            background = labelBackground,
            minWidth = TextComponent.MinWidth.fixed(40f), // Changed 40.dp  to 40f from library
        )
    val indicatorFrontComponent =
        rememberShapeComponent(fill(MaterialTheme.colorScheme.surface), CorneredShape.Pill)
    val guideline = rememberAxisGuidelineComponent()
    return rememberDefaultCartesianMarker(
        label = label,
        valueFormatter = valueFormatter,
        indicator =
            if (showIndicator) {
                { color ->
                    LayeredComponent(
                        back = ShapeComponent(fill(color.copy(alpha = 0.15f)), CorneredShape.Pill),
                        front =
                            LayeredComponent(
                                back =
                                    ShapeComponent(
                                        fill = fill(color),
                                        shape = CorneredShape.Pill,
                                        shadow = shadow(radius = 12.dp, color = color),
                                    ),
                                front = indicatorFrontComponent,
                                padding = insets(5.dp),
                            ),
                        padding = insets(10.dp),
                    )
                }
            } else {
                null
            },
        indicatorSize = 36.dp,
        guideline = guideline,
    )
}

@Composable
fun MarketData(modifier: Modifier,
               pageState: CoinDetailPageState,
               coin: CoinMarketDetail ,
               featurevm: CoinDetailViewModel ){
    // Market Data
    Column(modifier = modifier )
    {
        // Market Data Options
        Row(modifier = Modifier.fillMaxWidth()
            .border(2.dp , color = Color.White.copy(alpha = 0f), shape = RoundedCornerShape(topStart = 35.dp , topEnd = 35.dp))
        ){
            Column(modifier = Modifier.weight(1f)
                .clickable{
                    featurevm.onEvent(
                        CoinDetailEvents.ChangeMarketOption(MarketOptions.Overview)
                    )
                }
                , horizontalAlignment = Alignment.CenterHorizontally ){
                Text(MarketOptions.Overview.value ,  style = Typography.bodyMedium,
                    modifier = Modifier
                        .padding(top = 10.dp , bottom = 6.dp)
                )
                Spacer(modifier = Modifier.fillMaxWidth(0.99f)
                    .height(2.dp)
                    .background(color =  when(pageState.SelectedData){
                        MarketOptions.Overview -> {
                            BrandColor
                        }
                        MarketOptions.About -> {
                            Color.Transparent
                        }
                    })
                )
            }
            Column(modifier = Modifier.weight(1f)
                  .clickable{
                     featurevm.onEvent(
                         CoinDetailEvents.ChangeMarketOption(MarketOptions.About)
                     ) },
                horizontalAlignment = Alignment.CenterHorizontally ){
                Text(text = MarketOptions.About.value , style = Typography.bodyMedium,
                    modifier = Modifier
                        .padding(top = 10.dp , bottom = 6.dp)
                )
                Spacer(modifier = Modifier.fillMaxWidth(0.99f)
                    .height(2.dp)
                    .background(color =  when(pageState.SelectedData){
                        MarketOptions.Overview -> {
                            Color.Transparent
                        }
                        MarketOptions.About -> {
                            BrandColor
                        }
                    })
                )
            }
        }

        AnimatedContent(targetState = pageState.SelectedData == MarketOptions.Overview ,
            content = { state ->
                if(state){
                    OverviewData(modifier = Modifier.fillMaxWidth(),
                        coin = coin,
                        pageState = pageState,
                        peform = { event ->
                            featurevm.onEvent( event )
                        },
                    )
                }else AboutContent(modifier = Modifier.fillMaxWidth(),
                    coin = coin)
            },
            transitionSpec = {
                if (pageState.SelectedData == MarketOptions.Overview) {
                  slideInHorizontally(
                      initialOffsetX = { it -> -it } // it = 480px and it goes to value you provided = -480px and intial start becomes -480px
                  )togetherWith slideOutHorizontally(
                      targetOffsetX = {
                          it -> it
                      } // since togtherwith is used to reverse or minus the width
                  // it used now It = -480px and the target end become -480px
                  )
                } else {
                    slideInHorizontally(
                        initialOffsetX = { it -> it }
                    ) togetherWith slideOutHorizontally(
                        targetOffsetX = {
                            it -> -it
                        }
                    )
                }
            }
        )

    }
}



@Composable
fun ChartTimeRow(modifier: Modifier = Modifier,
    peform: (CoinDetailEvents) -> Unit,
    pageState: CoinDetailPageState
){
    Row(modifier = modifier
        .padding(7.dp),
        horizontalArrangement = Arrangement.SpaceEvenly){
        ChartRange.entries.forEach { entry ->
            Box(modifier = Modifier
                    .background(color = if ( pageState.SelectedChartTime == entry) Color.White else Color.Transparent,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(4.dp)
                    .clickable {
                        if(pageState.coindata!=null)
                        peform(CoinDetailEvents.FetchChartData(coinid = pageState.coindata.id , range = entry))
                    }
            ){
                Text(text = entry.label,
                    style = Typography.bodyMedium.copy(
                        color = if ( pageState.SelectedChartTime == entry) BrandColor else Color.White
                    )
                )
            }
        }
    }
}

@Composable
fun OverviewData(modifier: Modifier = Modifier,
                 coin : CoinMarketDetail,
                 peform: (CoinDetailEvents) -> Unit,
                 pageState: CoinDetailPageState
){
    Column(modifier = modifier.padding(vertical = 10.dp , horizontal = 16.dp)
        .background(AppBg) ){

        // Low / High Bar
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween ,
            verticalAlignment = Alignment.CenterVertically) {

            Text("Low/High" , style = Typography.bodyMedium.copy(color = Color.White))

            // Choice Button
            Row(modifier = Modifier
                .border(1.dp , color = Color.Gray.copy(alpha = 0.2f) , shape = RoundedCornerShape(12.dp))
                .padding(7.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp) ){

                Box(modifier = Modifier
                    .background(color = when(pageState.timeline){
                        HighLowTime.Twenty4 -> Color.White
                        HighLowTime.AllTime -> Color.Transparent
                    } , shape = RoundedCornerShape(6.dp))
                    .padding(4.dp)
                    .clickable{
                        peform(CoinDetailEvents.ChangeHighLowTime(HighLowTime.Twenty4))
                    }

                ) {
                    Text(text = HighLowTime.Twenty4.type, style = Typography.bodySmall
                        .copy(color =  when(pageState.timeline){
                        HighLowTime.Twenty4 -> BrandColor
                        HighLowTime.AllTime -> Color.White }
                        )
                    )
                }
                Box(modifier = Modifier
                        .background(color =when(pageState.timeline){
                            HighLowTime.Twenty4 -> Color.Transparent
                            HighLowTime.AllTime -> Color.White
                        } , shape = RoundedCornerShape(6.dp))
                        .padding(4.dp)
                        .clickable{
                            peform(CoinDetailEvents.ChangeHighLowTime(HighLowTime.AllTime))
                        }
                ) {
                    Text(text = HighLowTime.AllTime.type, style = Typography.bodySmall.copy(
                        color =  when(pageState.timeline){
                            HighLowTime.Twenty4 -> Color.White
                            HighLowTime.AllTime -> BrandColor }
                       )
                    )
                }
            }
        }
        // High / Low Widget
        Box(modifier = Modifier.padding(vertical = 7.dp)) {
            when(pageState.timeline){
                HighLowTime.Twenty4 -> {
                    // Timeline with 24h Values
                    HighLowWidget(high = coin.market_data.high_24h.usd ,
                        low = coin.market_data.low_24h.usd,
                        currentprice = coin.market_data.current_price.usd ,
                    )
                }
                HighLowTime.AllTime -> {
                    // Timeline with All time values
                    HighLowWidget(high = coin.market_data.ath.usd ,
                        low = coin.market_data.atl.usd,
                        currentprice = coin.market_data.current_price.usd ,
                    )

                }
            }
        }


        MarketFundamentals(modifier = Modifier.fillMaxWidth()
            .padding(top = 16.dp),
            coin = coin
        )

    }
}

@Composable
fun HighLowWidget(high : Double,
                  low : Double,
                  currentprice : Double,
){
    Column(modifier = Modifier.fillMaxWidth()) {
        val med = ( (high + low) / 2)
        var progress by remember { mutableStateOf(0f) }

        LaunchedEffect(true) {
            progress =  if (high != low) {
                ((currentprice - low) / (high - low)).toFloat().coerceIn(0f, 1f)
                // Normalize Between 0 to 1

                // In Kotlin if a Float / Double is Divided by 0 , Kotlin Produces NaN , ie Non a Number and this can use the Ui to pause ,  which can break animations and UI updates.
                // We get 0 Double / 0.0 when the High / Low and current price is the same

                // Coerce is used  Confine Some Value between two Values and confirm the value stays between 0 to 1
            } else {
                1f  // Default to full progress when all values are the same
            }

        }

        // Loads with 0f as Progress on Initialization and when launched effect is launched the new progress or target  value it animates too
        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = tween( // wtf is tween and animation spec
                durationMillis = 2500,
                delayMillis = 200, // Delay Before Animation Starts
                easing = EaseInBounce // animation type
            )
        )

        Row(modifier = Modifier.fillMaxWidth(animatedProgress.coerceAtLeast(0.15f) + 0.02f), // Ensuring visibility
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$" + "%.2f".format(currentprice),
                style = Typography.bodySmall.copy(fontSize = 13.sp)
            )
            Icon(imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Price Indicator",
                tint = Color.White,
                modifier = Modifier.size(26.dp)
            )
        }
        Box(modifier = Modifier.fillMaxWidth()
            .height(7.dp)
            .border(0.5.dp , color = Color.Gray.copy(alpha = 0.2f) ,  shape = RoundedCornerShape(6.dp) )

        ) {
            val brush = Brush.horizontalGradient( colors =
                if( currentprice > med ) {
                    listOf(  Color.Green.copy(alpha = 0.1f) ,  Color.Green.copy(alpha = 0.7f) )
                }else {
                    listOf(  Color.Red.copy(alpha = 0.1f) , Color.Red.copy(alpha = 0.7f) ,)
                }
            )
            Box(modifier = Modifier.fillMaxWidth(animatedProgress.coerceAtLeast(0.02f))  // if the value is 0 at least show some green / red color
                .fillMaxHeight()
                .background(brush = brush, shape = RoundedCornerShape(6.dp))
                .animateContentSize() // Why use this, cause even without the modifier seems like it is expanding

            )
        }

        Row(modifier = Modifier
            .padding(top = 4.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "$" + "%.4f".format(low), style = Typography.bodySmall)
            Text(text = "$" + "%.4f".format(high), style = Typography.bodySmall)
        }
    }
}
@Composable
fun MarketFundamentals(modifier: Modifier,
                       coin : CoinMarketDetail) {
    Column(modifier = modifier ,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Row(modifier = Modifier.fillMaxWidth() ,
            horizontalArrangement = Arrangement.SpaceBetween) {
            FundamentalItemLeft(label = "Market Cap",
                value = "$" + "%.2f".format(coin.market_data.market_cap.usd) ,
                modifier = Modifier.weight(1f)
            )
            FundamentalItemRight(label = "Market Rank",
                value =  coin.market_data.market_cap_rank.toString() ,
                modifier = Modifier.weight(1f)
            )

        }
        Row(modifier = Modifier.fillMaxWidth() ,
            horizontalArrangement = Arrangement.SpaceBetween) {
            FundamentalItemLeft(label = "Total Volume",
                value = "$" + "%.2f".format(coin.market_data.total_volume.usd) ,
                modifier = Modifier.weight(1f)
            )
            FundamentalItemRight(label = "Fully Dilated Value",
                value = "$" + "%.2f".format(coin.market_data.fully_diluted_valuation.usd) ,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
@Composable
fun FundamentalItemLeft(label : String, value : String, modifier: Modifier ){
    Column(modifier = modifier){
        Text(text = label , style = Typography.bodySmall
            .copy(color = Color.White.copy(alpha = 0.5f))
        )
        Text( text = value , style = Typography.bodySmall.copy(fontSize = 16.sp) )
    }
}
@Composable
fun FundamentalItemRight(label : String, value : String, modifier: Modifier ){
    Column(modifier = modifier ,
        horizontalAlignment = Alignment.End){
        Text(text = label , style = Typography.bodySmall
            .copy(color = Color.White.copy(alpha = 0.5f))
        )
        Text( text = value , style = Typography.bodySmall.copy(fontSize = 16.sp) )
    }
}

@Composable
fun AboutContent(modifier: Modifier ,
                 coin : CoinMarketDetail){
    Column(modifier = modifier) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
        ){
            Row(modifier = Modifier.fillMaxWidth()
                .padding(bottom = 12.dp)
                , horizontalArrangement = Arrangement.SpaceBetween ,
                verticalAlignment = Alignment.CenterVertically ){


                Text(text = "What is " + coin.symbol + "?" , style = Typography.titleSmall ,
                    modifier = Modifier)


                val context = LocalContext.current
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        val uri = coin.links.homepage?.get(0)?.toUri()
                        val openLink = Intent(Intent.ACTION_VIEW , uri)
                        context.startActivity(openLink)
                    } ) {
                        Icon(imageVector = ImageVector.vectorResource(R.drawable.website) , contentDescription = "Website Link",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Image(imageVector = ImageVector.vectorResource(R.drawable.reddit) , contentDescription = "Reddit Link",
                        modifier = Modifier.size(27.dp)
                            .clickable{

                                if(coin.links.subreddit_url != null){
                                    val uri = coin.links.subreddit_url.toUri()
                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                    context.startActivity(intent)
                                }

                            }
                    )

                }

            }
            Text(text = coin.description.en , style = Typography.bodySmall)
        }
        Spacer(modifier = Modifier.fillMaxWidth()
            .height(2.dp)
            .background(color = Color.Gray.copy(alpha = 0.2f))
        )
    }

}
