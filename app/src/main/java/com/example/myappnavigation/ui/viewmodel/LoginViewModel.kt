package com.example.myappnavigation.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myappnavigation.data.database.AppDatabase
import com.example.myappnavigation.data.local.entity.UsuarioEntity
import com.example.myappnavigation.data.repository.UsuarioRepository
import com.example.myappnavigation.model.LoginModel
import com.example.myappnavigation.model.Validaciones
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var selectedRol by mutableStateOf("DOCENTE")
        private set

    var uiState by mutableStateOf(LoginModel(email = "m.lopez@escuela.cl", password = "123"))
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onRolSelected(rol: String) {
        selectedRol = rol
        if (rol == "DOCENTE") {
            uiState = uiState.copy(email = "m.lopez@escuela.cl", emailError = null, passwordError = null)
        } else {
            uiState = uiState.copy(email = "admin@escuela.cl", emailError = null, passwordError = null)
        }
        errorMessage = null
    }

    fun onEmailChanged(email: String) {
        // Valida en vivo solo si el campo ya mostraba un error
        val error = if (uiState.emailError != null) Validaciones.validarEmail(email) else null
        uiState = uiState.copy(email = email, emailError = error)
        errorMessage = null
    }

    fun onPasswordChanged(password: String) {
        val error = if (uiState.passwordError != null) Validaciones.validarPasswordLogin(password) else null
        uiState = uiState.copy(password = password, passwordError = error)
        errorMessage = null
    }

    fun onLoginClick(context: Context, onSuccess: (user: UsuarioEntity) -> Unit) {
        val email = uiState.email
        val password = uiState.password

        val emailError = Validaciones.validarEmail(email)
        val passwordError = Validaciones.validarPasswordLogin(password)
        uiState = uiState.copy(emailError = emailError, passwordError = passwordError)
        if (emailError != null || passwordError != null) {
            errorMessage = null
            return
        }

        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val repository = UsuarioRepository(db.usuarioDao())
            val user = repository.autenticar(email.trim(), password)

            if (user != null) {
                errorMessage = null
                onSuccess(user)
            } else {
                errorMessage = "Correo o contraseña incorrectos."
            }
        }
    }
}
