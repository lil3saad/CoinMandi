package com.example.coinmandi.userAuthentication.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinmandi.feature_home.presentation.state.HomePageEvents
import com.example.coinmandi.userAuthentication.domain.usecases.FormUseCases
import com.example.coinmandi.userAuthentication.domain.usecases.UserAuthUseCase
import com.example.coinmandi.userAuthentication.featurestate.UserAuthState
import com.example.coinmandi.userAuthentication.presentation.userregistration.state.CMUserState
import com.example.coinmandi.userAuthentication.presentation.userregistration.state.UserRegistrationEvents
import com.example.coinmandi.userAuthentication.presentation.login.state.LoginPageEvents
import com.example.coinmandi.userAuthentication.presentation.signup.state.AuthTextFeildState
import com.example.coinmandi.userAuthentication.presentation.signup.state.SignUpEvents
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class UserAuthViewModel(private val userauthUC : UserAuthUseCase ,
                        val formvalidaionUc: FormUseCases
) : ViewModel() {


    private var _userAuthState : MutableStateFlow<UserAuthState> = MutableStateFlow(UserAuthState.idle)
    val userauthstate = _userAuthState.asStateFlow()

    init {
      CheckAuthStatus()
    }
    fun CheckAuthStatus() {
        _userAuthState.value = UserAuthState.Loading
        viewModelScope.launch {
            _userAuthState.value =  userauthUC.CheckAuthStatus()
        }
    }


    private var _signupEmailFieldState = mutableStateOf(AuthTextFeildState())
    val signupemailfieldState  : State<AuthTextFeildState> =  _signupEmailFieldState

    private var _singuppasswordFieldState = mutableStateOf(AuthTextFeildState())
    val signuppasswordfieldState : State<AuthTextFeildState> =  _singuppasswordFieldState


    private var _repeatpassFieldState = mutableStateOf(AuthTextFeildState())
    val repeatPassword : State<AuthTextFeildState> = _repeatpassFieldState

    private var _SignupErrorMessage = mutableStateOf("" )
    val signupErrorMessage : State<String> = _SignupErrorMessage


    private val _isSignupFormValid = Channel<Boolean>()
    val issignupformValid = _isSignupFormValid.receiveAsFlow()

    // SignUpEvents
    fun onSignupEvent(event : SignUpEvents) {
        when(event){
            is SignUpEvents.EmailSignUp -> {
                _userAuthState.value = UserAuthState.Loading
                viewModelScope.launch {
                    _userAuthState.value =  userauthUC.CreateUserWith( email = event.email , password = event.password )
                }
            }
            is SignUpEvents.GoogleAuth -> {
                _userAuthState.value = UserAuthState.Loading
                viewModelScope.launch {
                    _userAuthState.value = userauthUC.SigninWithGoogle()
                }
            }
            SignUpEvents.ValidateForm -> {
                viewModelScope.launch {
                    _isSignupFormValid.send(
                        doValidateForm()
                    )
                }
            }
            is SignUpEvents.changeEmailfieldValue -> {
                _signupEmailFieldState.value = _signupEmailFieldState.value.copy(
                    fieldvalue = event.value
                )
            }
            is SignUpEvents.changePasswordfieldValue -> {
                _singuppasswordFieldState.value = _singuppasswordFieldState.value.copy(
                    fieldvalue = event.value
                )
            }
            is SignUpEvents.changeRepeatPasswordfieldValue -> {
                _repeatpassFieldState.value = _repeatpassFieldState.value.copy(
                    fieldvalue = event.value
                )
            }
            is SignUpEvents.ChangeError -> {
                _SignupErrorMessage.value = event.msg
            }
        }
    }
    private fun doValidateForm() : Boolean {

        val emailresult = formvalidaionUc.validateEmail(
            _signupEmailFieldState.value.fieldvalue
        )
        val passwordresult = formvalidaionUc.validatePassword(
            _singuppasswordFieldState.value.fieldvalue
        )
        val repeatpasswordresult = formvalidaionUc.validateRepeatPass(
            _singuppasswordFieldState.value.fieldvalue,
            _repeatpassFieldState.value.fieldvalue
        )

        val haserror : Boolean = listOf(
            emailresult,
            passwordresult,
            repeatpasswordresult
        ).any { result -> !result.isValidate }

        return if(haserror){
            _signupEmailFieldState.value = _signupEmailFieldState.value.copy(
                feildError = emailresult.ErrorMessage
            )
            _singuppasswordFieldState.value = _singuppasswordFieldState.value.copy(
                feildError = passwordresult.ErrorMessage
            )
            _repeatpassFieldState.value = _repeatpassFieldState.value.copy(
                feildError = repeatpasswordresult.ErrorMessage
            )
            false
        } else {
            _signupEmailFieldState.value = _signupEmailFieldState.value.copy(
                feildError = null
            )
            _singuppasswordFieldState.value = _singuppasswordFieldState.value.copy(
                feildError = null
            )
            _repeatpassFieldState.value = _repeatpassFieldState.value.copy(
                feildError = null
            )
            true
        }
    }

    private val _loginEmailField = mutableStateOf(AuthTextFeildState())
    val loginemailField : State<AuthTextFeildState> = _loginEmailField

    private val _loginPasswordField = mutableStateOf(AuthTextFeildState())
    val loignpasswordField : State<AuthTextFeildState> = _loginPasswordField

    private val _LoginErrorMessage = mutableStateOf("")
    val loginErrorMessage = _LoginErrorMessage

    private val _isLoginFormValid = Channel<Boolean>()
    val isloginformValid = _isLoginFormValid.receiveAsFlow()
    // LoginEvents
    fun onLoginEvent( event : LoginPageEvents){
        when(event){
            LoginPageEvents.IntiateGoogleAuth -> {
                _userAuthState.value = UserAuthState.Loading
                viewModelScope.launch {
                    _userAuthState.value = userauthUC.SigninWithGoogle()
                }
            }
            is LoginPageEvents.Login -> {
                _userAuthState.value = UserAuthState.Loading
                viewModelScope.launch {
                    _userAuthState.value = userauthUC.SignInUserWith(event.email , event.password)
                }

            }
            is LoginPageEvents.ChangeEmailValue -> {
                _loginEmailField.value = _loginEmailField.value.copy(
                    fieldvalue = event.value
                )
            }
            is LoginPageEvents.ChangePasswordValue ->{
                _loginPasswordField.value  = _loginPasswordField.value.copy(
                    fieldvalue = event.value
                )
            }
            is LoginPageEvents.ValidateForm -> {
                viewModelScope.launch {
                    _isLoginFormValid.send(
                        ValidateEmailForm()
                    )
                }
            }
            is LoginPageEvents.ChangeErrorMessage ->{
                 _LoginErrorMessage.value = event.msg
            }
        }
    }
    private fun ValidateEmailForm() : Boolean {
          val emailresult = formvalidaionUc.validateEmail(
              _loginEmailField.value.fieldvalue
          )
          val passwordresult = formvalidaionUc.validatePassword(
              _loginPasswordField.value.fieldvalue
          )

          val hasError = listOf(
              emailresult,
              passwordresult
          ).any { result -> ! result.isValidate }

          return if(hasError){
              _loginEmailField.value = _loginEmailField.value.copy(
                  feildError = emailresult.ErrorMessage
              )
              _loginPasswordField.value = _loginPasswordField.value.copy(
                  feildError = passwordresult.ErrorMessage
              )
              false
          }else {
              _loginEmailField.value = _loginEmailField.value.copy(
                  feildError = null
              )
              _loginPasswordField.value = _loginPasswordField.value.copy(
                  feildError = null
              )
              true
          }
    }


    private var _registerUserNameField = mutableStateOf(AuthTextFeildState())
    val registerUserNameField = _registerUserNameField

    private var _registerRealNameField = mutableStateOf(AuthTextFeildState())
    val registerRealNameField = _registerRealNameField

    private var _registerGooglePagePassword = mutableStateOf(AuthTextFeildState())
    val registergooglepagePassword : State<AuthTextFeildState> = _registerGooglePagePassword
    private var _registerGooglePageRepeatPass  = mutableStateOf(AuthTextFeildState())
    val registerGooglePageRepeatPass : State<AuthTextFeildState> = _registerGooglePageRepeatPass


    private var _isValidRegistration = Channel<Boolean>()
    val isvalidRegistration = _isValidRegistration.receiveAsFlow()



    private var _registrationState   : MutableStateFlow<CMUserState> = MutableStateFlow(CMUserState.Loading) // Let The Page Fetch WhichEver User
    val cmuserstate = _registrationState.asStateFlow()
    // Registration Events
    fun onRegistrationEvent(event : UserRegistrationEvents){
        when(event){
            is UserRegistrationEvents.CreateUser -> {
                _registrationState.value = CMUserState.Loading
                viewModelScope.launch {
                 _registrationState.value =   userauthUC.RegisterUserInFirestore(
                        event.user
                    )
                }
            }
            is UserRegistrationEvents.LinkGoogleAccountWith -> {
                _registrationState.value = CMUserState.Loading
                 viewModelScope.launch {
                     userauthUC.LinkEmailPasswordtoGoogleAuth(
                         event.user.email
                     ){ Issuccess , message ->
                         viewModelScope.launch {
                             if(Issuccess){
                                 _registrationState.value = CMUserState.GoogleLinkSuccess
                                 delay(2000) // Just To Tell The User Google Link Successfull
                                 _registrationState.value =  userauthUC.RegisterUserInFirestore(
                                     event.user
                                 )
                             }else {
                                 _registrationState.value = CMUserState.Error(
                                     message!!
                                 )
                             }
                         }
                     }
                 }
            }
            is UserRegistrationEvents.LinkEmailPasswordToGoogle -> {
                _registrationState.value = CMUserState.Loading
                viewModelScope.launch {
                    userauthUC.LinkGoogleAuthToEmailPassword(
                         event.user.email,
                         event.password,
                    ){ Issuccess , message ->
                        viewModelScope.launch {
                            if(Issuccess){
                                _registrationState.value = CMUserState.GoogleLinkSuccess
                              _registrationState.value =  userauthUC.RegisterUserInFirestore(
                                    event.user
                                )
                            }else {
                                _registrationState.value = CMUserState.Error(
                                    message!!
                                )
                            }
                        }
                    }
                }
            }
            UserRegistrationEvents.FetchUser -> {
               viewModelScope.launch {
                   _registrationState.value = userauthUC.FetchUserFromFirestore()
               }
            }

            is UserRegistrationEvents.ChangePasswordName -> {
                _registerGooglePagePassword.value = _registerGooglePagePassword.value.copy(
                    fieldvalue = event.value
                )
            }
            is UserRegistrationEvents.ChangeRepeatPasswordName -> {
                _registerGooglePageRepeatPass.value = _registerGooglePageRepeatPass.value.copy(
                    fieldvalue = event.value
                )
            }
            is UserRegistrationEvents.ChangeRealName -> {
                _registerRealNameField.value = _registerRealNameField.value.copy(
                    fieldvalue = event.value
                )
            }
            is UserRegistrationEvents.ChangeUserName -> {
                _registerUserNameField.value = _registerUserNameField.value.copy(
                    fieldvalue = event.value
                )
            }
            UserRegistrationEvents.ValidateEmailUserForm -> {
                viewModelScope.launch {
                    _isValidRegistration.send(
                        ValidateEmailRegisterForm()
                    )
                }
            }
            UserRegistrationEvents.ValidateGoogleUserForm -> {
                viewModelScope.launch {
                    _isValidRegistration.send(
                        ValidateGoogleRegisterForm()
                    )
                }

            }
        }
    }
    private fun ValidateGoogleRegisterForm() : Boolean{
        val usernameResult  = formvalidaionUc.validateUsername(
            _registerUserNameField.value.fieldvalue
        )
        val passwordResult = formvalidaionUc.validatePassword(
            _registerGooglePagePassword.value.fieldvalue
        )
        val repeatpassword = formvalidaionUc.validateRepeatPass(
            _registerGooglePagePassword.value.fieldvalue ,
            _registerGooglePageRepeatPass.value.fieldvalue
        )

        val hasError  = listOf(
            usernameResult,
            passwordResult,
            repeatpassword,
        ).any { result -> !result.isValidate }

        if(hasError){
            _registerUserNameField.value = _registerUserNameField.value.copy(
                feildError = usernameResult.ErrorMessage
            )
            _registerGooglePagePassword.value = _registerGooglePagePassword.value.copy(
                feildError = passwordResult.ErrorMessage
            )
            _registerGooglePageRepeatPass.value = _registerGooglePageRepeatPass.value.copy(
                feildError =  repeatpassword.ErrorMessage
            )
            return false
        }else{
            _registerUserNameField.value = _registerUserNameField.value.copy(
                feildError = null
            )
            _registerGooglePagePassword.value = _registerGooglePagePassword.value.copy(
                feildError = null
            )
            _registerGooglePageRepeatPass.value = _registerGooglePageRepeatPass.value.copy(
                feildError =  null
            )
            return true
        }

    }
    private fun ValidateEmailRegisterForm() : Boolean {

        val usernameResult  = formvalidaionUc.validateUsername(
            _registerUserNameField.value.fieldvalue
        )
        val realnameResult = formvalidaionUc.validateRealname(
            _registerRealNameField.value.fieldvalue
        )

        val hasError  = listOf(
            usernameResult,
            realnameResult
        ).any { result -> !result.isValidate }

        if(hasError){
            _registerUserNameField.value = _registerUserNameField.value.copy(
                feildError = usernameResult.ErrorMessage
            )
            _registerRealNameField.value = _registerRealNameField.value.copy(
                feildError = realnameResult.ErrorMessage
            )
            return false
        }else{
            _registerUserNameField.value = _registerUserNameField.value.copy(
                feildError = null
            )
            _registerRealNameField.value = _registerRealNameField.value.copy(
                feildError = null
            )

            return true
        }
    }

    fun onHomeEvent(event : HomePageEvents) {
        when(event){
            HomePageEvents.LogOutUser -> {
                val Firebaseaut = Firebase.auth
                Firebaseaut.signOut()
                _userAuthState.value = UserAuthState.idle
            }
            else -> Unit
        }
    }


}