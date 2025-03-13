package com.example.coinmandi.feature_useronboard.domain.usecases.formusecases

class ValidatePasswordUc {
    operator fun invoke(password : String) : ValidationResult {
        if(password.isBlank()){
            return ValidationResult(
                isValidate = false,
                ErrorMessage = "password required"
            )
        }
        if(password.length <6){
            return ValidationResult(
                isValidate = false,
                ErrorMessage = "password too short, min 6 characters"
            )
        }
        val isvalid : Boolean = password.any{ char -> char.isLetter() } && password.any { char -> char.isDigit() }
        if ( !isvalid ){
            return ValidationResult(
                isValidate = false,
                ErrorMessage = "password should contain letters and digits"
            )
        }
        return ValidationResult(
            isValidate = true,
            ErrorMessage = null
        )
    }
}