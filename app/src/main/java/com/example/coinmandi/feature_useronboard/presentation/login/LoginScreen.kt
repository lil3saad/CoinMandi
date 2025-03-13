package com.example.coinmandi.feature_useronboard.presentation.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.coinmandi.ui.theme.AppBg
import com.example.coinmandi.ui.theme.BodyFont
import com.example.coinmandi.ui.theme.BrandColor
import com.example.coinmandi.ui.theme.creativefont3
import com.example.coinmandi.R
import androidx.compose.ui.platform.LocalContext
import com.example.coinmandi.feature_useronboard.featurestate.UserAuthState
import com.example.coinmandi.feature_useronboard.presentation.navigation.AuthNavDestinations
import com.example.coinmandi.feature_useronboard.presentation.viewmodels.UserAuthViewModel
import com.example.coinmandi.core.presentation.navigation.CoreDestinations
import com.example.coinmandi.feature_useronboard.presentation.login.state.LoginPageEvents
import com.example.coinmandi.feature_useronboard.presentation.signup.AuthTextFeild
import com.example.coinmandi.feature_useronboard.presentation.signup.FilledButton
import kotlinx.coroutines.launch


@Preview(showBackground = true , showSystemUi = true , device = Devices.PIXEL_7_PRO)
@Composable
fun GreetingPreview() {

}
@Composable
fun LoginPage(modifier: Modifier = Modifier, navcontroller: NavHostController = rememberNavController() ,
              viewModel: UserAuthViewModel
) {
    Log.d("NAVIGATIONSAAD" , "LOGINPAGE LAUNCHED")
    val context = LocalContext.current

    var IsPageLoading by remember { mutableStateOf(false) }
    val ErrorMessage by viewModel.loginErrorMessage
    val loginstate by viewModel.userauthstate.collectAsState()
    LaunchedEffect(loginstate) {
        when(val state = loginstate) {
            UserAuthState.LoginSucces -> {
                navcontroller.navigate(CoreDestinations.CoreNavGraph){
                    popUpTo<CoreDestinations.AuthNavGraph>(){
                        inclusive = true
                    }
                }
            }
            is UserAuthState.GoogleUserSuccess -> {
                navcontroller.navigate(AuthNavDestinations.Registration)
            }
            is UserAuthState.LoginError -> {
                IsPageLoading = false
                viewModel.onLoginEvent(LoginPageEvents.ChangeErrorMessage(
                    state.msg
                ))
            }
            UserAuthState.Loading -> { IsPageLoading = true }
            else -> Unit
        }
    }

    Box(modifier = modifier.fillMaxSize().background(AppBg) , contentAlignment = Alignment.Center ){
        if(IsPageLoading){
                CircularProgressIndicator( color = Color.White , modifier = Modifier.size(150.dp))
        }else {
            // Page Content
            ConstraintLayout(modifier = Modifier.fillMaxSize().background(Color.Transparent).verticalScroll(rememberScrollState()) ){
                val (text1,text2, content) = createRefs()
                Text("COINM", style = TextStyle(fontSize = 120.sp , color = BrandColor.copy(alpha = 0.7f), fontFamily = creativefont3 , fontWeight = FontWeight.Bold) ,
                    softWrap = false,
                    modifier = Modifier
                        .constrainAs(text1){
                            top.linkTo(parent.top , margin = 40.dp )
                            end.linkTo(parent.end , margin = -30.dp)
                        }
                )
                LoginPageContent(modifier = Modifier.fillMaxWidth()
                    .constrainAs(content){
                        top.linkTo(text1.bottom , margin = 30.dp)
                        bottom.linkTo(text2.top , margin = 30.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }, navcontroller = navcontroller,
                    viewModel = viewModel,
                    errormessage = ErrorMessage
                )
                Text("MANDI", style = TextStyle(fontSize = 120.sp , color = BrandColor.copy(alpha = 0.7f) , fontFamily = creativefont3 , fontWeight = FontWeight.Bold) ,
                    softWrap = false,
                    modifier = Modifier
                        .constrainAs(text2){
                            bottom.linkTo(parent.bottom , margin = 40.dp )
                            start.linkTo(parent.start , margin = -30.dp)
                        }
                )
            }
        }
    }
}
@Composable
fun LoginPageContent(modifier: Modifier = Modifier, navcontroller: NavHostController = rememberNavController(),
                     viewModel:  UserAuthViewModel,
                     errormessage : String
){

    Column(modifier = modifier , horizontalAlignment = Alignment.CenterHorizontally) {

        val userid = viewModel.loginemailField
        AuthTextFeild(modifier = Modifier.fillMaxWidth(0.8f).padding(bottom = 20.dp) , textstate = userid , label = "Email" ,
            onvalueChange = { usertext ->
                viewModel.onLoginEvent(LoginPageEvents.ChangeEmailValue(
                    usertext
                ))
            }
            )
        val userpassword = viewModel.loignpasswordField
        AuthTextFeild(modifier = Modifier.fillMaxWidth(0.8f).padding(bottom = 20.dp) , textstate = userpassword , label = "Password",
            onvalueChange = { usertext ->
                viewModel.onLoginEvent(LoginPageEvents.ChangePasswordValue(
                    usertext
                ))
            }
        )
        if(errormessage.isNotBlank()){
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp) ,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(errormessage, style = TextStyle(
                    color = Color.Red.copy(alpha = 0.7f) ,
                    fontFamily = BodyFont,
                    fontSize = 15.sp
                )
                )

            }
        }
        val scope  = rememberCoroutineScope()
        FilledButton(text = "Login" , onclick = { viewModel.onLoginEvent(LoginPageEvents.ValidateForm)
            scope.launch {
                viewModel.isloginformValid.collect{ result ->
                    if(result){
                        viewModel.onLoginEvent((LoginPageEvents.Login(
                            email = userid.value.fieldvalue,
                            password = userpassword.value.fieldvalue))
                        )
                    }
                }
            }
        })
        OAuthRow(modifier = Modifier.fillMaxWidth(0.9f).padding(vertical = 20.dp) ,
            Text = "Login" ,
            googleonclick = {
              viewModel.onLoginEvent(LoginPageEvents.IntiateGoogleAuth)
            }
        )

    }
}
@Composable
fun OAuthRow(modifier: Modifier = Modifier , Text : String , googleonclick : ()-> Unit) {
    Row(modifier = modifier , horizontalArrangement = Arrangement.SpaceBetween){
        Button(onClick = {

        },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray.copy(alpha = 0.2f),
                contentColor = Color.White
            ) ){
            Row(modifier = Modifier , verticalAlignment = Alignment.CenterVertically ) {
                Icon(imageVector = ImageVector.vectorResource(R.drawable.phone) , contentDescription = "Phone Icon" , modifier = Modifier.size(30.dp).padding(end = 6.dp) )
                Text("$Text with phone" , style = TextStyle( color = Color.White , fontSize = 12.sp , fontFamily = BodyFont , fontWeight = FontWeight.SemiBold)
                    )
            }
        }
        Button(onClick = {
            googleonclick()
        },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray.copy(alpha = 0.2f),
                contentColor = Color.White
            ) ){
            Row(modifier = Modifier , verticalAlignment = Alignment.CenterVertically ) {
                Image(imageVector = ImageVector.vectorResource(R.drawable.googlelogo) , contentDescription = "Google Icon" , modifier = Modifier.size(30.dp).padding(end = 6.dp) )
                Text("$Text with google" , style = TextStyle( color = Color.White , fontSize = 12.sp , fontFamily = BodyFont , fontWeight = FontWeight.SemiBold)
                )
            }
        }
    }
}
