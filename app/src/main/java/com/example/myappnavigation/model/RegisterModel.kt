package com.example.myappnavigation.model

data class RegisterModel(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val errorMessage: String? = null
) {
    val tieneErrores: Boolean
        get() = listOf(nameError, emailError, passwordError, confirmPasswordError).any { it != null }
}
