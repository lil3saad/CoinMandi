package com.example.coinmandi.feature_useronboard.domain.usecases.formusecases

class ValidateUserNameUc {
    operator fun invoke(username : String) : ValidationResult{
        if(username.isBlank()){
            return ValidationResult(
                isValidate = false,
                ErrorMessage = "please choose a username"
            )
        }
        if(username.length < 6){
            return ValidationResult(
                isValidate = false,
                ErrorMessage = "username should have min 6 letters"
            )
        }
        return ValidationResult(
            isValidate = true,
            ErrorMessage = null
        )
    }
}