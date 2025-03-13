package com.example.coinmandi.feature_useronboard.domain.usecases.formusecases

class ValidateRealNameUc {
    operator fun invoke(realname : String) : ValidationResult{
        if(realname.isBlank()){
            return ValidationResult(
                isValidate = false,
                ErrorMessage = "please enter your fullname"
            )
        }
       return ValidationResult(
            isValidate = true,
            ErrorMessage = null
        )
    }
}