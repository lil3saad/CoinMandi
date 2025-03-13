package com.example.coinmandi.feature_useronboard.presentation.userregistration.components

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coinmandi.R
import com.example.coinmandi.feature_useronboard.domain.model.CoinMandiUser
import com.example.coinmandi.ui.theme.AppBg
import com.example.coinmandi.ui.theme.BodyFont
import com.example.coinmandi.ui.theme.BrandColor
import com.example.coinmandi.ui.theme.HeadingFont
import com.example.coinmandi.feature_useronboard.domain.model.EmailUserData
import com.example.coinmandi.feature_useronboard.presentation.signup.AuthTextFeild
import com.example.coinmandi.feature_useronboard.presentation.userregistration.state.UserRegistrationEvents
import com.example.coinmandi.feature_useronboard.presentation.signup.FilledButton
import com.example.coinmandi.feature_useronboard.presentation.signup.OutlineButton
import com.example.coinmandi.feature_useronboard.presentation.viewmodels.UserAuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterUserEmailContent(modifier: Modifier = Modifier,
                             viewmodel : UserAuthViewModel,
                             userdata: EmailUserData
){


    val profileurl = remember { mutableStateOf("") }

    var showAlert by remember { mutableStateOf(false) }
    var AlertMessage by remember { mutableStateOf("Error") }

    Box(modifier = Modifier.fillMaxSize()
    ){
        Column(modifier = modifier.fillMaxSize().background(AppBg) , horizontalAlignment = Alignment.CenterHorizontally , verticalArrangement = Arrangement.Center) {


            val activityresult = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia() ) { uri ->
                if(uri !=null){
                    Log.d("PROFILEPIC" , "${uri.scheme} , $uri")
                    // Convert The Uri to Url or Store the Uri in App write Storage
                }
            }
            Box(modifier = Modifier.fillMaxWidth() , contentAlignment = Alignment.BottomEnd) {
                Row(modifier = Modifier.fillMaxWidth() , horizontalArrangement = Arrangement.Center){
                    Image( imageVector = ImageVector.vectorResource(R.drawable.defaultprofile),
                        "default Profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.padding(bottom = 50.dp).size(200.dp)
                            .clip(CircleShape)
                            .clickable {
                                // Select Image From
                                activityresult.launch( PickVisualMediaRequest() )
                            }
                    )
                }
                Icon(imageVector = Icons.Default.Add , contentDescription = "Pick Profile Picture" ,
                    modifier = Modifier.size(100.dp))
            }

            Text("What should coinmandi call you?" , style = TextStyle(fontSize = 18.sp , color = Color.White , fontFamily = HeadingFont , fontWeight = FontWeight.Bold) ,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            val userUsername = viewmodel.registerUserNameField
            AuthTextFeild( textstate = userUsername , modifier = Modifier.fillMaxWidth(0.9f).padding(bottom = 25.dp) , label = "Username",
                onvalueChange = {usertext ->
                    viewmodel.onRegistrationEvent(
                        UserRegistrationEvents.ChangeUserName(
                            value = usertext
                        )
                    )

                }
            )
            val userRealname = viewmodel.registerRealNameField
            AuthTextFeild( textstate = userRealname , modifier = Modifier.fillMaxWidth(0.9f).padding(bottom = 55.dp) , label = "Fullname",
                onvalueChange = {usertext ->
                    viewmodel.onRegistrationEvent(
                        UserRegistrationEvents.ChangeRealName(
                            value = usertext
                        )
                    )

                }
            )
            val scope = rememberCoroutineScope()
            OutlineButton(text = "Create Account with only Email") { viewmodel.onRegistrationEvent(UserRegistrationEvents.ValidateEmailUserForm)
                scope.launch {
                    viewmodel.isvalidRegistration.collect { valid ->
                        if(valid) {
                            Log.d("TAGY" , "Form is Valid")
                            viewmodel.onRegistrationEvent(
                                UserRegistrationEvents.CreateUser(
                                    CoinMandiUser(
                                        uid = Firebase.auth.uid!! ,
                                        realname = userRealname.value.fieldvalue ,
                                        cm_username = userUsername.value.fieldvalue,
                                        email = userdata.email,
                                        profileurl = profileurl.value))
                            ) // Register User in FireBase
                        }
                    }
                }
            }
            Text("Linking your google account makes login secure and smooth" , style = TextStyle(color = BrandColor , fontSize = 12.sp , fontFamily = BodyFont , fontWeight =  FontWeight.Normal),
                modifier = Modifier.padding(bottom = 50.dp)
            )
            FilledButton(text = "Continue with google account"){
                // Link Email / Password with Google Auth
                // Register User in FireBase
                viewmodel.onRegistrationEvent(UserRegistrationEvents.ValidateEmailUserForm)
                scope.launch {
                    viewmodel.isvalidRegistration.collect{ valid ->
                        if(valid) // Register User in FireBase
                            viewmodel.onRegistrationEvent(
                                UserRegistrationEvents.LinkGoogleAccountWith(
                                    user = CoinMandiUser(
                                        uid = Firebase.auth.uid!! ,
                                        realname = userRealname.value.fieldvalue ,
                                        cm_username = userUsername.value.fieldvalue,
                                        email = userdata.email,
                                        profileurl = profileurl.value)
                                )
                            )
                    }
                }
            }
        }

        if(showAlert){
            BasicAlertDialog(onDismissRequest = {
                showAlert = false
            },
                modifier = Modifier.height(300.dp).fillMaxWidth().background(Color.Black.copy(alpha = 0.5f))
                    .border(1.dp , color = Color.White.copy(0.1f))
            ) {
                Column(modifier = Modifier.fillMaxWidth() , verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(AlertMessage, style = TextStyle(fontSize = 25.sp , color = Color.Red) , modifier = Modifier.padding(bottom = 25.dp, start = 10.dp , end = 10.dp))
                    FilledButton("Done") {
                        showAlert = false
                    }
                }

            }
        }
    }

}