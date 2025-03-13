package com.example.coinmandi.feature_useronboard.domain.usecases

import android.content.Context
import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import java.security.MessageDigest
import java.util.UUID
import com.example.coinmandi.R
import com.example.coinmandi.feature_useronboard.domain.model.CoinMandiUser
import com.example.coinmandi.feature_useronboard.domain.model.EmailUserData
import com.example.coinmandi.feature_useronboard.domain.model.GoogleUserData
import com.example.coinmandi.feature_useronboard.featurestate.UserAuthState
import com.example.coinmandi.feature_useronboard.presentation.userregistration.state.CMUserState
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthActionCodeException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWebException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class UserAuthUseCase(private val context : Context,
                      private val firebaseauth : FirebaseAuth,
                      private val firestore : FirebaseFirestore,
                      private val credmanager : CredentialManager
) {

    // Check AuthStatus
   suspend fun CheckAuthStatus() : UserAuthState {
       return if(firebaseauth.currentUser != null){
           Log.d("TAGY"   , "WHY ARE YOU RUNNING FROM 39")
            val FireStoreState = FetchUserFromFirestore()
            when(FireStoreState){
                is CMUserState.Read -> {
                      UserAuthState.Loggedin
                }
                else -> UserAuthState.LoggedOut
            }
        }else {
            UserAuthState.LoggedOut
        }
   }


    // Google OAuth
    suspend fun SigninWithGoogle() : UserAuthState =
        try {
            val result : GetCredentialResponse = BuildCredentialRequest()
            HandleSignIn(result)
        }catch ( e  : Exception) {
            Log.d("TAGY" , "FROM HERE ONLY")
            e.printStackTrace()
            if(e is CancellationException) throw  e
            UserAuthState.SignUpError(e.message!!)
        }
    private suspend fun BuildCredentialRequest() : GetCredentialResponse {

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false) //
            .setAutoSelectEnabled(false) // If Only one account , or previously selected account , it auto logins in without letting you choose with google account
            .setNonce(CreateNonce()) // Nonce is a Security Algorithm , Just Use From  Google
            .setServerClientId(context.getString(R.string.google_web_client)) // FireBase Google Auth Client Id
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption).build()

        return credmanager.getCredential(context = context , request = request)
    }
    private fun CreateNonce() : String{
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()

        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        return digest.fold("") { str , it ->
            str + "02x".format(it)
        }
    }
    private suspend fun HandleSignIn(credrespone : GetCredentialResponse) : UserAuthState {

        val usercreds : Credential = credrespone.credential
        if(usercreds is CustomCredential &&  usercreds.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){ // Check For the Right User Credential Type
            try {
                val googleuserdata = GoogleIdTokenCredential.createFrom(usercreds.data) // Get Google UserData from user google Credentials
                Log.d("TAGY" , "User Created In FireBase With ${googleuserdata.id}")

                val googleidtoken = googleuserdata.idToken // User Google Token Id from UserData

                val firebasecreds = GoogleAuthProvider.getCredential(googleidtoken , null) // Get FireBase Creds from the GoogleIdToken
                val result =  firebaseauth.signInWithCredential(firebasecreds).await()

                if(result != null){
                    val firebaseuser = result.user!!
                    val userdata = GoogleUserData( realname = firebaseuser.displayName , uid = firebaseuser.uid , email = firebaseuser.email!! , profilepic = firebaseuser.photoUrl.toString())
                    Log.d("TAGY" , "User Created In FireBase With $userdata")
                    return UserAuthState.GoogleUserSuccess(
                        userdata
                    )
                }else {
                    return UserAuthState.SignUpError("FIREBASE GOOGLE AUTH OPERATION FAILED")
                }
            }catch (e : GoogleIdTokenParsingException){
                return UserAuthState.SignUpError(e.message!!)
            }
        }else {
            // Wrong Type Credential
            return UserAuthState.SignUpError("Wrong Credential Type")
        }
    }
    suspend fun LinkEmailPasswordtoGoogleAuth(email: String, onResult: (Boolean, String?) -> Unit)  {
        try {
            val user = firebaseauth.currentUser
            if (user != null) {
                val googletokenid : String? = GetUserGoogleTokenId(email)

                if(googletokenid == "SAME_EMAIL"){
                    onResult(false , "Google account email does not match your email, please retry with same email")
                }else {
                    val credential = GoogleAuthProvider.getCredential(googletokenid , null)
                    val task = user.linkWithCredential(credential).await()
                    if (task != null) {
                        onResult(true, "Google Account linked successfully!")
                    } else {
                        onResult(false, "Database Error")
                    }
                }
            } else {
                onResult(false, "No user is logged in")
            }
        }catch (e : Exception){
            if(e is CancellationException) throw e
            e.printStackTrace()
            onResult(false, e.message!!)
        }
    }

    private suspend  fun GetUserGoogleTokenId(email: String ) : String? {
        val credreponse = BuildCredentialRequest()
        val usercreds = credreponse.credential

        if(usercreds is CustomCredential &&  usercreds.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){
            try {
                val googleuserdata = GoogleIdTokenCredential.createFrom(usercreds.data)
                if(email != googleuserdata.id){
                    return "SAME_EMAIL"
                }else {
                    val googleidtoken = googleuserdata.idToken
                    return googleidtoken
                }
            }catch (e : Exception){
                Log.d("TAGY" , "${e.message}")
                return "${e.message}"
            }
        }else {
            Log.d("TAGY" , "Wrong Crendential Type")
            return "Wrong Crendential Type"
        }
    }





    suspend fun LinkGoogleAuthToEmailPassword(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
       try {
           val user = firebaseauth.currentUser
           if (user != null) {
               val credential = EmailAuthProvider.getCredential(email, password)
               val task = user.linkWithCredential(credential).await()
               if (task != null) {
                   onResult(true, "Email linked successfully!")
               } else {
                   onResult(false, "Failed to Link User")
               }
           } else {
               onResult(false, "No user is signed in.")
           }
       }catch (e : Exception){
           if(e is CancellationException) throw e
           e.printStackTrace()
           onResult(false, e.message!!)
       }
   }



    suspend fun CreateUserWith(email: String, password: String) : UserAuthState  = try {
       firebaseauth.createUserWithEmailAndPassword(email , password).await()
            UserAuthState.EmailUserSuccess(
                EmailUserData(email , password)
            )
        }
        catch (e : FirebaseException){
         e.printStackTrace()
        if(e is FirebaseAuthUserCollisionException) UserAuthState.SignUpError(e.message+ " Please Login ")
        else UserAuthState.SignUpError(e.message!!)
     }

     // Why Was The Whole Screen Begin Called Again / Meaning LoginStateSuccessFull MutlitpleTimes
       suspend fun SignInUserWith(email : String, password: String) : UserAuthState = try{
       firebaseauth.signInWithEmailAndPassword(email , password).await()
        UserAuthState.LoginSucces
       }
       catch (e : FirebaseException){
           e.printStackTrace()
           if(e is FirebaseAuthInvalidCredentialsException) UserAuthState.LoginError("Email or Password is Incorrect")
           else UserAuthState.LoginError(e.message!!)
       }



       suspend fun RegisterUserInFirestore(user : CoinMandiUser) : CMUserState =
       try {
           val  curruser = firebaseauth.currentUser!!
             firestore.collection("Users").document(curruser.uid).set(user).await()
               CMUserState.UserCreated
       }catch (e : FirebaseException){
           e.printStackTrace()
           Log.d("TAGY" , e.message!!)
           CMUserState.Error(e.message!!)
       }

            suspend fun FetchUserFromFirestore() : CMUserState =
            try {
                val  curruser = firebaseauth.currentUser
                if(curruser != null){
                    val doc = firestore.collection("Users").document(curruser.uid).get().await()
                    if( !doc.exists()) {
                        CMUserState.idle
                    }else {
                        val userRealName : String= doc.getString("realname")!!
                        val userCmUserName : String= doc.getString("cm_username")!!
                        val userEmail = doc.getString("email")!!
                        val userUID = doc.getString("uid")!!
                        val userProfileUrl = doc.getString("profileurl")!!

                        val user =  CoinMandiUser(
                            uid = userUID,
                            realname = userRealName,
                            cm_username = userCmUserName,
                            email  = userEmail,
                            profileurl = userProfileUrl
                        )
                        CMUserState.Read(user)
                    }
                }else {
                    CMUserState.idle
                }
            } catch (e : FirebaseException){
                  e.printStackTrace()
                  Log.d("TAGY" , e.message!!)
                  // This Should Never Happen
                  CMUserState.FetchUserError(e.message!!)
            }

}

// A nounce is Basically a Encrypted Id Using Some Encryption Algorithm