package com.example.coinmandi.feature_useronboard.presentation.signup

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.coinmandi.feature_useronboard.presentation.navigation.AuthNavDestinations

import com.example.coinmandi.feature_useronboard.presentation.login.OAuthRow
import com.example.coinmandi.ui.theme.AppBg
import com.example.coinmandi.ui.theme.BrandColor
import com.example.coinmandi.ui.theme.creativefont3
import com.example.coinmandi.feature_useronboard.featurestate.UserAuthState
import com.example.coinmandi.feature_useronboard.presentation.signup.state.AuthTextFeildState
import com.example.coinmandi.feature_useronboard.presentation.signup.state.SignUpEvents
import com.example.coinmandi.feature_useronboard.presentation.viewmodels.UserAuthViewModel
import com.example.coinmandi.ui.theme.BodyFont
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun SignUpPage(modifier: Modifier = Modifier, navcontroller: NavHostController = rememberNavController(),
               viewmodel : UserAuthViewModel
){

    Log.d("NAVIGATIONSAAD" , "SignUpPage LAUNCHED")
    val context = LocalContext.current

    var IsPageLoading by remember { mutableStateOf(false) }
    var ErrorMessage =  viewmodel.signupErrorMessage.value

    val pagestate by viewmodel.userauthstate.collectAsState()
    LaunchedEffect(pagestate) {
        when(val state = pagestate){
            is UserAuthState.EmailUserSuccess -> {
                // collect Email UserData on the other Side
                navcontroller.navigate(AuthNavDestinations.Registration)
            }
            is UserAuthState.GoogleUserSuccess -> {
                // Collect GoogleUserData on the Other Sice
                navcontroller.navigate(AuthNavDestinations.Registration)
            }
            is UserAuthState.SignUpError -> {
                IsPageLoading = false
                viewmodel.onSignupEvent(SignUpEvents.ChangeError(
                    state.msg
                ))
            }
            UserAuthState.Loading -> {
                IsPageLoading = true
            }
           else -> Unit
        }
    }
    Box(modifier = modifier.fillMaxSize().background(AppBg) , contentAlignment = Alignment.Center ){
        if(IsPageLoading){
            CircularProgressIndicator( color = Color.White , modifier = Modifier.size(150.dp))
        }else {
            ConstraintLayout(modifier = Modifier.fillMaxSize().background(Color.Transparent)
                .verticalScroll(rememberScrollState())  )
            {

                val (text1,text2, content) = createRefs()
                Text("COINM", style = TextStyle(fontSize = 120.sp , color = BrandColor.copy(alpha = 0.7f), fontFamily = creativefont3 , fontWeight = FontWeight.Bold) ,
                    softWrap = false,
                    modifier = Modifier
                        .constrainAs(text1){
                            top.run { linkTo(parent.top , margin = 20.dp ) }
                            end.linkTo(parent.end , margin = -30.dp)
                        }
                )
                SignUpPageContent(modifier = Modifier.fillMaxWidth()
                    .constrainAs(content){
                        top.linkTo(text1.bottom , margin = 40.dp)
                        bottom.linkTo(text2.top, margin = 40.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }, navcontroller = navcontroller,
                    viewmodel = viewmodel,
                    ErrorMessage = ErrorMessage
                )
                Text("MANDI", style = TextStyle(fontSize = 120.sp , color = BrandColor.copy(alpha = 0.7f) , fontFamily = creativefont3 , fontWeight = FontWeight.Bold) ,
                    softWrap = false,
                    modifier = Modifier
                        .constrainAs(text2){
                            bottom.linkTo(parent.bottom , margin = 20.dp )
                            start.linkTo(parent.start , margin = -30.dp)
                        }
                )

            }
        }
    }
}
@Composable
fun SignUpPageContent(modifier: Modifier = Modifier,
                      navcontroller: NavHostController = rememberNavController() ,
                      viewmodel: UserAuthViewModel,
                      ErrorMessage : String
){
    Column(modifier = modifier , horizontalAlignment = Alignment.CenterHorizontally) {
        val emailstate = viewmodel.signupemailfieldState
        AuthTextFeild(modifier = Modifier.fillMaxWidth(0.8f).padding(bottom = 10.dp) , textstate = emailstate , label = "Email" ,
            onvalueChange = { usertext ->
                viewmodel.onSignupEvent(SignUpEvents.changeEmailfieldValue(
                    usertext)
                )
            }
        )
        val userpassword = viewmodel.signuppasswordfieldState
        AuthTextFeild(modifier = Modifier.fillMaxWidth(0.8f).padding(bottom = 10.dp), textstate = userpassword , label = "Password" ,
            onvalueChange = { usertext ->
                viewmodel.onSignupEvent(SignUpEvents.changePasswordfieldValue(
                    usertext)
                )
            }
        )
        val userConfirmpassword = viewmodel.repeatPassword
        AuthTextFeild(modifier = Modifier.fillMaxWidth(0.8f).padding(bottom = 20.dp) , textstate = userConfirmpassword, label = "Confirm Password" ,
            onvalueChange = { usertext ->
              viewmodel.onSignupEvent(SignUpEvents.changeRepeatPasswordfieldValue(
                    usertext)
                )
            }
        )
        if(ErrorMessage.isNotBlank()){
             Row(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp) ,
                 horizontalArrangement = Arrangement.Center
             ) {

                 Text(ErrorMessage, style = TextStyle(
                     color = Color.Red.copy(alpha = 0.7f) ,
                     fontFamily = BodyFont,
                     fontSize = 15.sp
                   )
                 )

             }
        }
        val scope = rememberCoroutineScope()
        FilledButton( text = "Sign-up" , onclick = { viewmodel.onSignupEvent(SignUpEvents.ValidateForm)
            scope.launch {
                viewmodel.issignupformValid.collectLatest { value: Boolean ->
                    if(value){
                        Log.d("TAGY" , "FORM VALIDATED ")
                        viewmodel.onSignupEvent( SignUpEvents.EmailSignUp(
                            email = emailstate.value.fieldvalue ,
                            password = userpassword.value.fieldvalue)
                        )
                    } else Unit
                }
            }
        })
        OAuthRow(modifier = Modifier.fillMaxWidth(0.95f).padding(top = 40.dp),
            Text = "Sign-up" , googleonclick =  {
                viewmodel.onSignupEvent(SignUpEvents.GoogleAuth)
            }
        )
    }
}

@Composable
fun AuthTextFeild(modifier: Modifier = Modifier, label : String,
                  textstate : State<AuthTextFeildState>,
                  onvalueChange : (usertext : String) -> Unit
){
    val softwarekeyboard = LocalSoftwareKeyboardController.current!!
    Column(modifier = modifier) {
        Text("$label" , style = TextStyle(fontFamily = BodyFont , fontWeight = FontWeight.Bold , color = Color.White , fontSize = 15.sp) , modifier = Modifier.padding(bottom = 10.dp) )
        OutlinedTextField(value = textstate.value.fieldvalue , onValueChange = { usertext ->
            onvalueChange(usertext)
        },
            isError = textstate.value.feildError != null,
            maxLines = 1,
            shape = RoundedCornerShape(250.dp),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.White,
                unfocusedContainerColor = Color.Transparent,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.White,
                focusedTextColor = Color.White,
                cursorColor = Color.White,
                errorTextColor = Color.White,
                errorContainerColor = Color.Transparent,
                errorCursorColor = Color.Red
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    softwarekeyboard.hide()
                }
            ),
            modifier = Modifier.fillMaxWidth().border(1.dp, color = Color.White , shape = RoundedCornerShape(250.dp) )
        )
        if(textstate.value.feildError != null){
            Text( text = textstate.value.feildError ?: "",
                style = TextStyle(fontFamily = BodyFont , fontWeight = FontWeight.Bold , color = Color.Red.copy(
                    alpha = 0.7f), fontSize = 13.sp),
                modifier = Modifier.padding(start = 20.dp)
            )
        }

    }
}