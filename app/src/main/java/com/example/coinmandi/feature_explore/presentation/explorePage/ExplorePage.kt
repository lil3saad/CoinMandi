package com.example.coinmandi.feature_explore.presentation.explorePage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.coinmandi.core.presentation.CoreViewModel
import com.example.coinmandi.feature_explore.presentation.viewmodels.ExploreViewModel
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.coinmandi.core.presentation.components.BottomNavBar
import com.example.coinmandi.core.presentation.navigation.CoreDestinations
import com.example.coinmandi.core.presentation.states.CorePageState
import com.example.coinmandi.feature_explore.presentation.explorePage.components.CategoryListBox
import com.example.coinmandi.feature_explore.presentation.explorePage.components.CategoryMenu
import com.example.coinmandi.feature_explore.presentation.explorePage.components.ExplorePageSearchBar
import com.example.coinmandi.feature_explore.presentation.explorePage.components.TrendingCoins
import com.example.coinmandi.feature_explore.presentation.states.ExplorePageState
import com.example.coinmandi.feature_explore.presentation.states.SelectedCategoryState
import com.example.coinmandi.ui.theme.BrandColor
import com.example.coinmandi.ui.theme.HeadingFont

@Composable
fun ExplorePage(modifier: Modifier = Modifier ,
                navcontroller : NavHostController ,
                pageViewModel : ExploreViewModel = koinViewModel<ExploreViewModel>(),
                coreViewModel: CoreViewModel
){
    coreViewModel.changeAppBarsState(state = CorePageState.ExplorePage)
    val PageState = pageViewModel.PageState.value
    LaunchedEffect(Unit) {
        pageViewModel.GetTrendingList()
        pageViewModel.GetCategoryCoins(SelectedCategoryState.RealWorldAsset)
    }
    Box(modifier = modifier.fillMaxSize()) {
        ExplorePageContent(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.95f),
            navcontroller = navcontroller,
            pageState = PageState,
            pageViewModel = pageViewModel
        )
        //Bottom NavBar
        Box(modifier = Modifier.fillMaxSize().background(color = Color.Transparent) , contentAlignment = Alignment.BottomCenter) {
            val barstate by coreViewModel.bottomBarState.collectAsState()
            BottomNavBar(
                modifier = Modifier.fillMaxWidth(),
                barstate =barstate ,
                Homeclick = {
                    navcontroller.navigate(CoreDestinations.HomeNavGraph) {
                        launchSingleTop = true // Only One Type of Destination Can be On Top , If Duplicates Found they are popped
                        popUpTo(CoreDestinations.ExploreNavGraph){
                            inclusive = true // Including the Current Graph
                        }
                    }
                },
                Userprofileclick = {
                    navcontroller.navigate( CoreDestinations.UserProfileNavGraph ){
                        launchSingleTop = true // Only One Type of Destination Can be On Top , If Duplicates Found they are popped
                        popUpTo(CoreDestinations.ExploreNavGraph){
                            inclusive = true // Including the Current Graph
                        }
                    }
                },
                AiBothclick = {
                    navcontroller.navigate( CoreDestinations.AIBotNavGraph ){
                        launchSingleTop = true // Only One Type of Destination Can be On Top , If Duplicates Found they are popped
                        popUpTo(CoreDestinations.ExploreNavGraph){
                            inclusive = true // Including the Current Graph
                        }
                    }
                },
                Exploreclick = {},

                )
        }
    }
}
@Composable
fun ExplorePageContent(modifier: Modifier = Modifier,
                       pageViewModel : ExploreViewModel,
    navcontroller: NavHostController,
    pageState: ExplorePageState
){
    Column(modifier = modifier ,
        horizontalAlignment = Alignment.CenterHorizontally){
        var searchtext = remember { mutableStateOf("") }
        ExplorePageSearchBar(searchText = searchtext,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp)
        )
        TrendingCoins(modifier = Modifier,
            pagestate = pageState
        )
        // Category Row
        Row(modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 20.dp , vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically ){
            Text(text =  if( pageState.SelectedCategory != null)
                pageState.SelectedCategory.catergoryname
                 else "RWA",
                style = TextStyle(color = Color.White , fontSize = 25.sp , fontFamily = HeadingFont , fontWeight = FontWeight.ExtraBold)
            )
            Icon(imageVector = if( pageState.IsCateogoryMenuVisible ) Icons.Default.Close
                else Icons.Default.ArrowDropDown,
                contentDescription = "DropDown",
                tint = if(pageState.IsCateogoryMenuVisible ) BrandColor
                else Color.White ,
                modifier = Modifier.size(35.dp)
                    .clickable{
                        if(pageState.IsCateogoryMenuVisible ) pageViewModel.toogleCategoryMenu( toggle = false)
                        else pageViewModel.toogleCategoryMenu( toggle = true)
                    }
            )
        }
        AnimatedVisibility(visible =  pageState.IsCateogoryMenuVisible ,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(), // What is This EnterTransition,
            modifier = Modifier.background(color = Color.Transparent)) {
            CategoryMenu(pageViewModel = pageViewModel,
                modifier = Modifier.fillMaxWidth(),
                pagestate = pageState
            )
        }
        CategoryListBox(modifier = Modifier.fillMaxWidth().fillMaxHeight(),
           state = pageState
        )
    }
}




