package com.example.coinmandi.feature_home.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.coinmandi.core.presentation.CoreViewModel
import com.example.coinmandi.core.presentation.components.BottomNavBar
import com.example.coinmandi.feature_home.presentation.state.HomePageEvents
import com.example.coinmandi.feature_home.presentation.state.HomePageState
import com.example.coinmandi.feature_useronboard.domain.model.CoinMandiUser
import com.example.coinmandi.feature_useronboard.presentation.viewmodels.UserAuthViewModel
import com.example.coinmandi.core.presentation.navigation.CoreDestinations
import com.example.coinmandi.core.presentation.states.CorePageState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomePage(modifier: Modifier = Modifier, navcontroller : NavHostController ,
             viewModel: HomeViewModel = koinViewModel(), // Scopes ViewModels to the Composable I guess,
             coreViewModel: CoreViewModel,
             userauthvm : UserAuthViewModel
             ){

    coreViewModel.changeAppBarsState(state = CorePageState.HomePage)
    Log.d("NAVIGATIONSAAD" , "HOME SCREEN LAUNCHED")
    val pagestate by viewModel.pageState.collectAsState()
    val context  = LocalContext.current

    var userdata by remember { mutableStateOf( CoinMandiUser("")) }
    LaunchedEffect(pagestate) {
        when(val state = pagestate) {
            HomePageState.idle -> {
                Log.d("TAGY" , "FETCHING USER DATA")
                viewModel.onEvent(HomePageEvents.FetchUserData)
            }
            is HomePageState.Error -> {
                Toast.makeText(context , state.message, Toast.LENGTH_LONG).show()
            }
            is HomePageState.ReadUser -> {
                Log.d("TAGY" , "UserData ${state.user} Fetched")
                      userdata = state.user
            }
        }
    }

    Box(modifier = modifier.fillMaxSize() , contentAlignment = Alignment.Center) {
        Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.BottomCenter) {
            val barstate by coreViewModel.bottomBarState.collectAsState()
            BottomNavBar(modifier = Modifier.fillMaxWidth(),
                barstate = barstate ,
                Exploreclick = {
                    navcontroller.navigate(CoreDestinations.ExploreNavGraph)
                },
                AiBothclick = {
                    navcontroller.navigate(CoreDestinations.AIBotNavGraph)
                },
                Userprofileclick = {
                    navcontroller.navigate(CoreDestinations.UserProfileNavGraph)
                },
                Homeclick = {}
            )
        }
        Column{
            Text("   ${userdata.realname}" , fontSize = 15.sp , color = Color.White)
            Button(onClick = {
                userauthvm.onHomeEvent(HomePageEvents.LogOutUser)
                navcontroller.navigate(route = CoreDestinations.AuthNavGraph){
                    popUpTo<CoreDestinations.CoreNavGraph>(){
                        inclusive = true
                    }
                }
            }
            ){
                Text("Sign Out")
            }
        }

    }
}