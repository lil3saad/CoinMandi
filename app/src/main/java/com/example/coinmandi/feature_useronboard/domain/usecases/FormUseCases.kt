package com.example.coinmandi.feature_useronboard.domain.usecases

import com.example.coinmandi.feature_useronboard.domain.usecases.formusecases.ValidateEmailUc
import com.example.coinmandi.feature_useronboard.domain.usecases.formusecases.ValidatePasswordUc
import com.example.coinmandi.feature_useronboard.domain.usecases.formusecases.ValidateRealNameUc
import com.example.coinmandi.feature_useronboard.domain.usecases.formusecases.ValidateRepeatPass
import com.example.coinmandi.feature_useronboard.domain.usecases.formusecases.ValidateUserNameUc

class FormUseCases(val validateEmail : ValidateEmailUc,
    val validatePassword : ValidatePasswordUc,
    val validateRepeatPass : ValidateRepeatPass,
    val validateUsername : ValidateUserNameUc,
    val validateRealname : ValidateRealNameUc
)