package com.example.coinmandi.feature_home.domain

import android.util.Log
import com.example.coinmandi.feature_home.presentation.state.HomePageState
import com.example.coinmandi.userAuthentication.domain.model.CoinMandiUser
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class GetCMuserUsecase(val firebaseauth : FirebaseAuth,
    val firestore: FirebaseFirestore
){
       suspend operator fun invoke() : HomePageState =
       try {
           val  curruser = firebaseauth.currentUser
           if(curruser != null){
               val doc = firestore.collection("Users").document(curruser.uid).get().await()
               if( !doc.exists()) {
                   Log.d("TAGY" , "")
                   HomePageState.Error("No User Found")
               }else {
                   val userRealName : String= doc.getString("realname")!!
                   val userCmUserName : String= doc.getString("cm_username")!!
                   val userEmail = doc.getString("email")!!
                   val userUID = doc.getString("uid")!!
                   val userProfileUrl = doc.getString("profileurl")!!

                   HomePageState.ReadUser(
                       user =  CoinMandiUser(
                           uid = userUID,
                           realname = userRealName,
                           cm_username = userCmUserName,
                           email  = userEmail,
                           profileurl = userProfileUrl)
                   )
               }
           }else {
               HomePageState.Error("No User Found")
           }

       }
       catch (e : FirebaseException){
                e.printStackTrace()
                Log.d("TAGY" , e.message!!)
              HomePageState.Error(e.message!!)
       }
}