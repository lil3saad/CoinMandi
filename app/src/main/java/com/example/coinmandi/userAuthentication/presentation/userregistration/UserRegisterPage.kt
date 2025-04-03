package com.example.coinmandi.userAuthentication.presentation.userregistration

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.coinmandi.ui.theme.AppBg
import com.example.coinmandi.userAuthentication.featurestate.UserAuthState
import com.example.coinmandi.userAuthentication.presentation.userregistration.state.AuthUserData
import com.example.coinmandi.userAuthentication.presentation.userregistration.state.CMUserState
import com.example.coinmandi.userAuthentication.presentation.userregistration.components.RegisterUserEmailContent
import com.example.coinmandi.userAuthentication.presentation.userregistration.components.RegisterUserGoogleContent
import com.example.coinmandi.userAuthentication.presentation.userregistration.state.UserRegistrationEvents
import com.example.coinmandi.userAuthentication.presentation.viewmodels.UserAuthViewModel
import com.example.coinmandi.core.presentation.navigation.CoreDestinations

@Composable
fun RegisterUserPage(modifier: Modifier = Modifier ,
    navcontroller : NavHostController,
    viewModel: UserAuthViewModel
){
    Log.d("NAVIGATIONSAAD" , "REGISTRATION PAGE LAUNCHED")
    viewModel.onRegistrationEvent(UserRegistrationEvents.FetchUser)
    val context = LocalContext.current
    var IsLoading = remember { mutableStateOf(false) }
    val CmUserstate by viewModel.cmuserstate.collectAsState()
    LaunchedEffect(CmUserstate) {
        when(val state = CmUserstate) {
            is CMUserState.Error -> {
                IsLoading.value = false
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }
            is CMUserState.FetchUserError -> {
                IsLoading.value = false
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }
            is CMUserState.GoogleLinkSuccess -> {
                Toast.makeText(context, "Your Google Account Connected", Toast.LENGTH_LONG).show()
                IsLoading.value = false
            }
            is CMUserState.Loading -> {
                IsLoading.value = true
            }
            is CMUserState.Read -> {
                // User Already Exists In FireStore
                IsLoading.value = false
                navcontroller.navigate(CoreDestinations.CoreNavGraph){
                    popUpTo<CoreDestinations.AuthNavGraph>(){
                        inclusive = true
                    }
                }
            }
            is CMUserState.UserCreated -> {
                IsLoading.value = false
                navcontroller.navigate(CoreDestinations.CoreNavGraph){
                    popUpTo<CoreDestinations.AuthNavGraph>(){
                        inclusive = true
                    }
                }
            }
            is CMUserState.idle -> {
                IsLoading.value = false
            }
        }
    }


    val authstate by viewModel.userauthstate.collectAsState()
    var userdata by remember { mutableStateOf( AuthUserData()) }
    // Add this LaunchedEffect to handle authstate changes
    LaunchedEffect(authstate) {
        when (val state = authstate) {
            is UserAuthState.EmailUserSuccess -> {
                val ob =  userdata.copy(
                    EmailUser = state.userData
                )
                userdata = ob
                IsLoading.value = false
            }
            is UserAuthState.GoogleUserSuccess -> {
                val ob =  userdata.copy(
                    GoogleUser = state.userdata
                )
                userdata = ob
                IsLoading.value = false

            }
            else -> Unit
        }
    }

    Box(modifier = modifier.fillMaxSize()
        .background(AppBg)
        .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ){
           if (IsLoading.value){
               CircularProgressIndicator(
                   modifier = Modifier.size(200.dp),
                   color = Color.White
               )
           }else {
               when {
                   userdata.EmailUser != null -> {
                       Log.d("CHECKUSERDATA" , "$userdata from 114" )
                       RegisterUserEmailContent(
                           modifier = modifier.fillMaxSize(),
                           viewmodel = viewModel,
                           userdata = userdata.EmailUser!!
                       )
                   }
                   userdata.GoogleUser != null -> {
                       Log.d("CHECKUSERDATA" , "$userdata from 122" )
                       RegisterUserGoogleContent(
                           modifier = modifier.fillMaxSize(),
                           userdata = userdata.GoogleUser!!,
                           viewmodel = viewModel
                       )
                   }
               }
           }

    }
}