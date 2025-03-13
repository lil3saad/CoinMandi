package com.example.coinmandi.feature_useronboard.presentation.signup

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.coinmandi.ui.theme.AppBg
import com.example.coinmandi.ui.theme.BrandColor
import com.example.coinmandi.R
import com.example.coinmandi.feature_useronboard.presentation.navigation.AuthNavDestinations
import com.example.coinmandi.ui.theme.BodyFont
import com.example.coinmandi.ui.theme.HeadingFont
import com.example.coinmandi.feature_useronboard.featurestate.UserAuthState
import com.example.coinmandi.feature_useronboard.presentation.viewmodels.UserAuthViewModel
import com.example.coinmandi.core.presentation.navigation.CoreDestinations


@Composable
fun AuthPage(modifier: Modifier = Modifier, navcontroller: NavHostController = rememberNavController() ,
             viewModel: UserAuthViewModel
){
    Log.d("NAVIGATIONSAAD" , "INITIAL AUTH PAGE LAUNCHED")
    val authstate by viewModel.userauthstate.collectAsState()
    val isLoading = remember { mutableStateOf(true) }
    LaunchedEffect(authstate) {
        when(authstate){
            UserAuthState.Loading -> {
                isLoading.value = true
            }
            UserAuthState.Loggedin -> {
                navcontroller.navigate(CoreDestinations.CoreNavGraph){
                    popUpTo<CoreDestinations.AuthNavGraph>(){
                        inclusive = false
                    }
                }
            }
            else -> {
                isLoading.value = false
            }
        }
    }


    if(isLoading.value){
        Box(modifier.fillMaxSize().background(AppBg) , contentAlignment = Alignment.Center)
        {
            CircularProgressIndicator( color = Color.White , modifier = Modifier.size(150.dp))
        }
    }else {
        Box(modifier = modifier.fillMaxSize().background(AppBg).verticalScroll(rememberScrollState()) , contentAlignment = Alignment.Center) {
            Box(modifier = Modifier.fillMaxSize().padding(top = 50.dp) , contentAlignment = Alignment.Center){
                Image(imageVector = ImageVector.vectorResource(R.drawable.coinmandilogo) , contentDescription = "AppLogo",
                    modifier = Modifier.padding(bottom = 300.dp).size(220.dp)
                )
                Icon(imageVector = ImageVector.vectorResource(R.drawable.blockchain) ,
                    contentDescription = "LogoBackground", tint = Color.White.copy(alpha = 0.4f),
                    modifier = Modifier.padding(bottom = 300.dp).size(500.dp)
                )
                Column(modifier = Modifier.fillMaxWidth().padding(top = 320.dp)){
                    Text("Buy Sell and Know cryptocurrencies. " , color = Color.White , fontSize = 21.sp , fontFamily = HeadingFont , fontWeight = FontWeight.Bold , textAlign = TextAlign.Center , modifier = Modifier.fillMaxWidth())
                    Text("currency of tomorrow on coinmandi" , color = Color.White , fontSize = 21.sp , fontFamily = HeadingFont , fontWeight = FontWeight.Bold  , textAlign = TextAlign.Center , modifier = Modifier.fillMaxWidth() )
                }
            }
            Row(modifier = Modifier.fillMaxSize().padding( top = 600.dp) , horizontalArrangement = Arrangement.Center) {
                OutlineButton(text = "Signup For Free", onclick = {
                    navcontroller.navigate(AuthNavDestinations.SignupPage)
                })
                Spacer(modifier = Modifier.size(15.dp))
                FilledButton(text = "Login" , onclick =  {navcontroller.navigate(AuthNavDestinations.LoginPage("", 1)) }
                )

            }

        }
    }

}

@Composable
fun OutlineButton(text : String , onclick : () -> Unit) {
    Button(onClick = { onclick() },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color.Transparent
        ),
        border = BorderStroke(1.5.dp , BrandColor)
    ) {
        Text(text , fontSize = 18.sp , fontFamily = BodyFont , fontWeight = FontWeight.Medium)
    }
}
@Composable
fun FilledButton(text : String , onclick : () -> Unit ) {
    Button(onClick = { onclick() },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = BrandColor
        )
    ) {
        Text(text , fontSize = 20.sp , fontFamily = BodyFont , fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TopAppGraident(modifier: Modifier = Modifier) {
    val colorstops = arrayOf(
        0f to BrandColor.copy(alpha = 0.1f),
        0.2f to AppBg
    )
    val brush = Brush.verticalGradient(
        colorStops = colorstops,
    )
    Box(modifier = modifier.background(brush))
}
@Composable
fun BottomAppGraident(modifier: Modifier = Modifier) {
    val colorstops = arrayOf(
        0.7f to AppBg,
        1f to BrandColor.copy(alpha = 0.1f),
    )
    val brush = Brush.verticalGradient(
        colorStops = colorstops,
    )
    Box(modifier = modifier.background(brush))
}