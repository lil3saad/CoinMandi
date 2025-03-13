package com.example.coinmandi.feature_userprofile.presentation.profilePage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.coinmandi.core.presentation.CoreViewModel
import com.example.coinmandi.core.presentation.components.BottomNavBar
import com.example.coinmandi.core.presentation.navigation.CoreDestinations
import com.example.coinmandi.core.presentation.states.CorePageState
import com.example.coinmandi.ui.theme.AppBg
import androidx.compose.runtime.getValue

@Composable
fun ProfilePage(
    modifier: Modifier = Modifier ,
    navcontroller : NavHostController ,
    coreViewModel: CoreViewModel
){
    coreViewModel.changeAppBarsState(state = CorePageState.ProfilePage)
    Box(modifier = modifier.fillMaxSize() , contentAlignment = Alignment.Center) {
        Box(modifier = Modifier.fillMaxSize().background(AppBg) , contentAlignment = Alignment.BottomCenter) {
            val barstate by coreViewModel.bottomBarState.collectAsState()
            BottomNavBar(
                modifier = Modifier.fillMaxWidth(),
                barstate =barstate ,
                Homeclick = {
                    navcontroller.navigate(CoreDestinations.HomeNavGraph) {
                        launchSingleTop = true // Only One Type of Destination Can be On Top , If Duplicates Found they are popped
                        popUpTo(CoreDestinations.UserProfileNavGraph){
                            inclusive = true // Including the Current Graph
                        }
                    }
                },
                Exploreclick = {
                    navcontroller.navigate(CoreDestinations.ExploreNavGraph){
                        launchSingleTop = true // Only One Type of Destination Can be On Top , If Duplicates Found they are popped
                        popUpTo(CoreDestinations.UserProfileNavGraph){
                            inclusive = true // Including the Current Graph
                        }
                    }
                },
                AiBothclick = {
                    navcontroller.navigate( CoreDestinations.AIBotNavGraph ){
                        launchSingleTop = true // Only One Type of Destination Can be On Top , If Duplicates Found they are popped
                        popUpTo(CoreDestinations.UserProfileNavGraph){
                            inclusive = true // Including the Current Graph
                        }
                    }
                },
                Userprofileclick = {}
            )
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Text("ProfilePage" , fontSize = 55.sp , color = Color.White)
        }
    }
}
