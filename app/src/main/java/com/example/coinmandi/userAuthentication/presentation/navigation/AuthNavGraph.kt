package com.example.coinmandi.userAuthentication.presentation.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.coinmandi.userAuthentication.presentation.userregistration.RegisterUserPage
import com.example.coinmandi.userAuthentication.presentation.login.LoginPage
import com.example.coinmandi.userAuthentication.presentation.signup.AuthPage
import com.example.coinmandi.userAuthentication.presentation.signup.SignUpPage
import com.example.coinmandi.userAuthentication.presentation.viewmodels.UserAuthViewModel

fun NavGraphBuilder.UseronBoardingGrpah(
    modifier: Modifier , navcontroller: NavHostController , authviewmodel : UserAuthViewModel
){
    composable<AuthNavDestinations.AuthPage> {
        AuthPage( modifier = modifier , navcontroller, authviewmodel)
    }
    composable<AuthNavDestinations.LoginPage> { navbackstackentry ->
        LoginPage(modifier =  modifier,navcontroller , authviewmodel)
    }
    composable<AuthNavDestinations.SignupPage> {
        SignUpPage(modifier =  modifier,navcontroller , authviewmodel)
    }
    composable<AuthNavDestinations.Registration> {
        RegisterUserPage(modifier = modifier  , navcontroller = navcontroller , authviewmodel)
    }
}