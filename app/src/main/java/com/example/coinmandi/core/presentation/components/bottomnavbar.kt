package com.example.coinmandi.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.coinmandi.ui.theme.AppBg
import com.example.coinmandi.R
import com.example.coinmandi.core.presentation.states.CorePageState
import com.example.coinmandi.ui.theme.BrandColor


@Composable
fun BottomNavBar(modifier: Modifier = Modifier,
                 barstate : CorePageState,
                 Homeclick : () -> Unit = {},
                 Exploreclick : () -> Unit = {},
                 AiBothclick : () -> Unit = {},
                 Userprofileclick : () -> Unit = {}
){
    Box(modifier =  modifier.fillMaxWidth().height(intrinsicSize = IntrinsicSize.Min)
        .background(AppBg) ){
        BottomBarGradient(modifier = Modifier.fillMaxWidth().fillMaxHeight())
        Row(modifier = modifier.padding(vertical = 10.dp , horizontal = 70.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            BottomBarIcon(icon = when(barstate){
                CorePageState.AiBotPage -> R.drawable.homeoutline
                CorePageState.ExplorePage -> R.drawable.homeoutline
                CorePageState.HomePage -> R.drawable.homefilled
                CorePageState.ProfilePage -> R.drawable.homeoutline
                    else -> R.drawable.homeoutline
                                               },
                modifier = Modifier.size(21.dp).clickable{
                    Homeclick()
                }
            )
            BottomBarIcon(icon = when(barstate){
                CorePageState.AiBotPage -> R.drawable.exploreoutline
                CorePageState.ExplorePage -> R.drawable.explorefilled
                CorePageState.HomePage -> R.drawable.exploreoutline
                CorePageState.ProfilePage -> R.drawable.exploreoutline
                else -> R.drawable.homeoutline
            },
                modifier = Modifier.size(24.dp).clickable{
                    Exploreclick()
                }
            )
            BottomBarIcon(icon = when(barstate){
                CorePageState.AiBotPage -> R.drawable.aibotfilled
                CorePageState.ExplorePage ->  R.drawable.aibothoutline
                CorePageState.HomePage ->  R.drawable.aibothoutline
                CorePageState.ProfilePage ->  R.drawable.aibothoutline
                else -> R.drawable.homeoutline
            },
                modifier = Modifier.size(26.dp).clickable{
                    AiBothclick()
                }
            )
            BottomBarIcon(icon = when(barstate){
                CorePageState.AiBotPage -> R.drawable.useroutline
                CorePageState.ExplorePage -> R.drawable.useroutline
                CorePageState.HomePage -> R.drawable.useroutline
                CorePageState.ProfilePage ->  R.drawable.userfilled
                else -> R.drawable.homeoutline
            },
                modifier = Modifier.size(21.dp).clickable{
                    Userprofileclick()
                }
            )
        }
    }

}
@Composable
fun BottomBarGradient(
    modifier: Modifier
){
   val colorstops = arrayOf(
       0f to BrandColor.copy(alpha = 0.1f),
       1f to AppBg,
   )
   val brush = Brush.verticalGradient(
       colorStops = colorstops,
       startY =  Float.POSITIVE_INFINITY,
       endY = 0f
   )
   Box(modifier.background(brush))
}
@Composable
fun TopBarGradient(modifier: Modifier
){
   val colorstops = arrayOf(
       0f to BrandColor.copy(alpha = 0.1f),
       1f to AppBg,
   )
   val brush = Brush.verticalGradient(
       colorStops = colorstops,
       startY =  0f,
       endY = Float.POSITIVE_INFINITY
   )
   Box(modifier.background(brush))
}
@Composable
fun BottomBarIcon(modifier: Modifier = Modifier,
      icon : Int
){
    Icon(imageVector = ImageVector.vectorResource(icon) ,
        contentDescription = "Navigation Icon",
        tint = Color.White,
        modifier = modifier
    )
}