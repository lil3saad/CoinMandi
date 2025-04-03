package com.example.coinmandi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.coinmandi.core.presentation.CoreViewModel
import com.example.coinmandi.ui.theme.AppBg
import com.example.coinmandi.userAuthentication.presentation.navigation.AuthNavDestinations
import com.example.coinmandi.ui.theme.CoinMandiTheme
import com.example.coinmandi.userAuthentication.presentation.navigation.UseronBoardingGrpah
import com.example.coinmandi.userAuthentication.presentation.viewmodels.UserAuthViewModel
import com.example.coinmandi.core.presentation.navigation.CoreDestinations
import com.example.coinmandi.core.presentation.navigation.CoreNavGraph
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // When I Place ViewModel Here
        // When Home Screen Logs Out , it does so only till the auth page and auth page is in the Scaffold , the Reintialization of ViewModel Does not Happen and it Keeps the State which takes it to the home screen
        setContent {
            CoinMandiTheme {
                val navcontroller = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    containerColor = AppBg
                ){ innerPadding ->
                    NavHost(navController = navcontroller, startDestination = CoreDestinations.AuthNavGraph ) {
                        val authviewmodel = getViewModel<UserAuthViewModel>()
                        navigation<CoreDestinations.AuthNavGraph>(
                            startDestination = AuthNavDestinations.AuthPage
                        ){
                            Log.d("NAVIGATIONSAAD" , "AUTHGRAPH LAUNCHED")
                            UseronBoardingGrpah(
                                modifier = Modifier.padding(innerPadding),
                                navcontroller = navcontroller,
                                authviewmodel = authviewmodel
                            )
                        }
                        val coreviewmodel = getViewModel<CoreViewModel>()
                        navigation<CoreDestinations.CoreNavGraph>(startDestination =
                          CoreDestinations.HomeNavGraph
                        ){
                            Log.d("NAVIGATIONSAAD" , "CORE GRAPH LAUNCHED")
                            CoreNavGraph(
                                modifier = Modifier.padding(innerPadding),
                                navcontroller = navcontroller,
                                userauthvm = authviewmodel,
                                coreviewmodel = coreviewmodel
                            )
                        }
                    }
                }
            }
        }
    }



}




