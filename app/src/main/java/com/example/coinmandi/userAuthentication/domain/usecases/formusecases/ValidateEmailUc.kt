package com.example.coinmandi.userAuthentication.domain.usecases.formusecases

import android.util.Patterns

class ValidateEmailUc {
    operator fun invoke(email : String) : ValidationResult {
        if(email.isBlank()){
            return ValidationResult(
                isValidate = false,
                ErrorMessage = "Please Enter Your Email"
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() ){
            return ValidationResult(
                isValidate = false,
                ErrorMessage = "Wrong Email Format"
            )
        }
        return ValidationResult(
            isValidate = true,
            ErrorMessage = null
        )
    }
}