package com.example.coinmandi.feature_useronboard.presentation.userregistration.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import coil3.compose.AsyncImage
import com.example.coinmandi.feature_useronboard.domain.model.GoogleUserData
import com.example.coinmandi.R
import com.example.coinmandi.feature_useronboard.domain.model.CoinMandiUser
import com.example.coinmandi.feature_useronboard.presentation.signup.AuthTextFeild
import com.example.coinmandi.ui.theme.HeadingFont
import com.example.coinmandi.feature_useronboard.presentation.userregistration.state.UserRegistrationEvents

import com.example.coinmandi.feature_useronboard.presentation.signup.FilledButton
import com.example.coinmandi.feature_useronboard.presentation.viewmodels.UserAuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

@Composable
fun RegisterUserGoogleContent(modifier: Modifier = Modifier,
                              userdata: GoogleUserData,
                              viewmodel: UserAuthViewModel
){
    Column(modifier = modifier , horizontalAlignment = Alignment.CenterHorizontally , verticalArrangement = Arrangement.Center) {
            if (userdata.profilepic != null) {
                AsyncImage(model = userdata.profilepic,
                    contentDescription = "ProfilePicture",
                    modifier = Modifier.size(200.dp)
                        .size(200.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else Image(imageVector = ImageVector.vectorResource(R.drawable.defaultprofile),
                "default Profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp)
                    .clip(CircleShape)
                    .clickable {
                        // Select Image From
                    }
            )
            Text(text = if (userdata.realname == null){
                "Welcome"
            }else{
                "Welcome ${userdata.realname}"
            },
                style = TextStyle(fontSize = 22.sp , color = Color.White , fontFamily = HeadingFont , fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 12.dp , bottom = 50.dp)
            )
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
            val userUserpassword = viewmodel.registergooglepagePassword
            AuthTextFeild( textstate = userUserpassword , modifier = Modifier.fillMaxWidth(0.9f).padding(bottom = 25.dp) , label = "Set Password", onvalueChange = { usertext ->
                viewmodel.onRegistrationEvent(
                    UserRegistrationEvents.ChangePasswordName(
                        value = usertext
                    )
                )
                })
            val userconfirmpassword = viewmodel.registerGooglePageRepeatPass
            AuthTextFeild( textstate = userconfirmpassword , modifier = Modifier.fillMaxWidth(0.9f).padding(bottom = 25.dp) , label = "Confirm Password" , onvalueChange = { usertext ->
                         viewmodel.onRegistrationEvent(
                             UserRegistrationEvents.ChangeRepeatPasswordName(
                                 value = usertext
                             )
                         )
            })

            Text("Continue with ${userdata.email} ?" ,  style = TextStyle(fontSize = 18.sp , color = Color.White , fontFamily = HeadingFont , fontWeight = FontWeight.Bold) ,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            val scope = rememberCoroutineScope()
            FilledButton(text = "Create Account") {  viewmodel.onRegistrationEvent(UserRegistrationEvents.ValidateGoogleUserForm)
                scope.launch {
                    viewmodel.isvalidRegistration.collect{ valid ->
                        if(valid){
                            // Link Google Auth // With Email/Password // Register User In FireStore
                            viewmodel.onRegistrationEvent(
                                UserRegistrationEvents.LinkEmailPasswordToGoogle(
                                    CoinMandiUser(uid = Firebase.auth.currentUser!!.uid,
                                        realname = userdata.realname!!,
                                        cm_username = userUsername.value.fieldvalue ,
                                        email = userdata.email,
                                        profileurl = userdata.profilepic!!),
                                    password = userconfirmpassword.value.fieldvalue)
                            )
                        }
                    }
                }


            }
    }
}
