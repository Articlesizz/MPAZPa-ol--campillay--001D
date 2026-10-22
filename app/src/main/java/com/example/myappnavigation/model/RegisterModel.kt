package com.example.myappnavigation.model

data class RegisterModel(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorMessage: String? = null

)
