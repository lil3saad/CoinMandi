package com.example.coinmandi.userAuthentication.domain.usecases

import com.example.coinmandi.userAuthentication.domain.usecases.formusecases.ValidateEmailUc
import com.example.coinmandi.userAuthentication.domain.usecases.formusecases.ValidatePasswordUc
import com.example.coinmandi.userAuthentication.domain.usecases.formusecases.ValidateRealNameUc
import com.example.coinmandi.userAuthentication.domain.usecases.formusecases.ValidateRepeatPass
import com.example.coinmandi.userAuthentication.domain.usecases.formusecases.ValidateUserNameUc

class FormUseCases(val validateEmail : ValidateEmailUc,
    val validatePassword : ValidatePasswordUc,
    val validateRepeatPass : ValidateRepeatPass,
    val validateUsername : ValidateUserNameUc,
    val validateRealname : ValidateRealNameUc
)