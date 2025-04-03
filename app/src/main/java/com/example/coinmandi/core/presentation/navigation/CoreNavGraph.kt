package com.example.coinmandi.core.presentation.navigation

import android.util.Log
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.coinmandi.core.presentation.CoreViewModel
import com.example.coinmandi.core.feature_coindetail.navigation.CoinDetailDestinations
import com.example.coinmandi.core.feature_coindetail.presentation.CoinDetailPage
import com.example.coinmandi.feature_aibot.presentation.aibotScreen.AiBotPage
import com.example.coinmandi.feature_aibot.presentation.navigation.AiBothDestinations
import com.example.coinmandi.feature_explore.presentation.explorePage.ExplorePage
import com.example.coinmandi.feature_explore.presentation.navigation.ExploreDestinations
import com.example.coinmandi.feature_explore.presentation.searchpage.SearchPage
import com.example.coinmandi.userAuthentication.presentation.viewmodels.UserAuthViewModel
import com.example.coinmandi.feature_home.presentation.HomePage
import com.example.coinmandi.feature_home.presentation.navigation.HomeDestinations
import com.example.coinmandi.feature_userprofile.presentation.navigation.UserProfileDestiantions
import com.example.coinmandi.feature_userprofile.presentation.profilePage.ProfilePage


fun NavGraphBuilder.CoreNavGraph(
    modifier: Modifier ,
    navcontroller : NavHostController,
    userauthvm : UserAuthViewModel,
    coreviewmodel : CoreViewModel
){ // App Screens
    navigation<CoreDestinations.HomeNavGraph>(
        startDestination = HomeDestinations.HomeScreen
    ){
        Log.d("NAVIGATIONSAAD" , "Home GRAPH LAUNCHED")
        composable<HomeDestinations.HomeScreen>{
            HomePage(
                modifier = modifier,
                navcontroller,
                userauthvm = userauthvm ,
                coreViewModel = coreviewmodel
            )
        }
    }
    navigation<CoreDestinations.ExploreNavGraph>(
        startDestination = ExploreDestinations.ExplorePage()
    ){
        Log.d("NAVIGATIONSAAD" , "EXPLORE GRAPH LAUNCHED")
        composable< ExploreDestinations.ExplorePage>{
            ExplorePage(
                modifier = modifier,
                navcontroller = navcontroller,
                coreViewModel = coreviewmodel
            )
        }
        composable<ExploreDestinations.SearchPage> {
            SearchPage(modifier = modifier)
        }
    }

    navigation<CoreDestinations.CoinDetailNavGrap>(
        startDestination = CoinDetailDestinations.CoinDetailPage::class
    ) {
        composable<CoinDetailDestinations.CoinDetailPage> { backstackentry ->
          val data =  backstackentry.toRoute<CoinDetailDestinations.CoinDetailPage>()
              CoinDetailPage(
                  modifier = modifier,
                  coinId = data.coinid
              )
        }
    }
    navigation<CoreDestinations.AIBotNavGraph>(
        startDestination = AiBothDestinations.AiBothScreen()
    ){
        Log.d("NAVIGATIONSAAD" , "CORE  GRAPH LAUNCHED")
        composable<AiBothDestinations.AiBothScreen>{
           AiBotPage(
               modifier = modifier,
               navcontroller = navcontroller,
               coreviewmodel
           )
        }
    }
    navigation<CoreDestinations.UserProfileNavGraph>(
        startDestination = UserProfileDestiantions.UserProfilePage()
    ){
        Log.d("NAVIGATIONSAAD" , "USER PROFILE GRAPH LAUNCHED")
        composable<UserProfileDestiantions.UserProfilePage>{
            ProfilePage(
                modifier = modifier,
                navcontroller = navcontroller,
                coreviewmodel
            )
        }
    }

    // HomeScreenNavGraph
    // ExplorePageNavGraph
    // AI CRYPTO BOT NAV GRAPH
    // USER ACCOUNT SETTINGS NAV GRAPH
}