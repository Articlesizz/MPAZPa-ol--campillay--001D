package com.example.myappnavigation.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myappnavigation.data.database.AppDatabase
import com.example.myappnavigation.model.LoginModel
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var selectedRol by mutableStateOf("DOCENTE") // "DOCENTE" o "ENCARGADO_TI"
        private set

    var uiState by mutableStateOf(LoginModel(email = "m.lopez@escuela.cl", password = "123"))
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onRolSelected(rol: String) {
        selectedRol = rol
        if (rol == "DOCENTE") {
            uiState = uiState.copy(email = "m.lopez@escuela.cl")
        } else {
            uiState = uiState.copy(email = "admin@escuela.cl")
        }
        errorMessage = null
    }

    fun onEmailChanged(email: String) {
        uiState = uiState.copy(email = email)
        errorMessage = null
    }

    fun onPasswordChanged(password: String) {
        uiState = uiState.copy(password = password)
        errorMessage = null
    }

    fun onLoginClick(context: Context, onSuccess: (rol: String) -> Unit) {
        val email = uiState.email
        val password = uiState.password

        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Ingresa correo y contraseña."
            return
        }

        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val user = db.usuarioDao().getUserByEmail(email)

            if (user != null && user.password == password) {
                errorMessage = null
                onSuccess(user.rol) // Devuelve el rol del usuario autenticado
            } else {
                errorMessage = "Correo o contraseña incorrectos."
            }
        }
    }
}
