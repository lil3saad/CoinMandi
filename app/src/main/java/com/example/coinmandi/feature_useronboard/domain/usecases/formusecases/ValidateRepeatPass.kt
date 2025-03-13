package com.example.coinmandi.feature_useronboard.domain.usecases.formusecases

class ValidateRepeatPass {
    operator fun invoke(password : String , repeatpassword : String) : ValidationResult {
        if(!password.equals(repeatpassword )){
            return ValidationResult(
                isValidate = false,
                ErrorMessage = "passwords do not match"
            )
        }
        return ValidationResult(
            isValidate = true,
            ErrorMessage = null
        )
    }
}