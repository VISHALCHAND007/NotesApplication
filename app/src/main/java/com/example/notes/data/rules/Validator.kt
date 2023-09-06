package com.example.notes.data.rules

object Validator {
    fun validateUsername(username: String): ValidationResult {
        return ValidationResult(!username.isNullOrBlank() && username.length >= 4)
    }
    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(!email.isNullOrBlank())
    }
    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(!password.isNullOrBlank() && password.length >= 4)
    }
}
data class ValidationResult(
    val result: Boolean = false
)