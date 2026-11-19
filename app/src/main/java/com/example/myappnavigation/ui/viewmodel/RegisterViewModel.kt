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
import com.example.myappnavigation.model.RegisterModel
import com.example.myappnavigation.model.Validaciones
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    var uiState by mutableStateOf(RegisterModel())
        private set

    // Cada campo se revalida en vivo una vez que ya mostró un error
    fun onNameChanged(name: String) {
        val error = if (uiState.nameError != null) Validaciones.validarNombre(name) else null
        uiState = uiState.copy(name = name, nameError = error, errorMessage = null)
    }

    fun onEmailChanged(email: String) {
        val error = if (uiState.emailError != null) Validaciones.validarEmail(email) else null
        uiState = uiState.copy(email = email, emailError = error, errorMessage = null)
    }

    fun onPasswordChanged(password: String) {
        val error = if (uiState.passwordError != null) Validaciones.validarPasswordRegistro(password) else null
        val confirmError = if (uiState.confirmPasswordError != null)
            Validaciones.validarConfirmacion(password, uiState.confirmPassword) else null
        uiState = uiState.copy(
            password = password,
            passwordError = error,
            confirmPasswordError = confirmError,
            errorMessage = null
        )
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        val error = if (uiState.confirmPasswordError != null)
            Validaciones.validarConfirmacion(uiState.password, confirmPassword) else null
        uiState = uiState.copy(confirmPassword = confirmPassword, confirmPasswordError = error, errorMessage = null)
    }

    /** Valida todos los campos y marca los errores. Devuelve true si el formulario es válido. */
    fun validarFormulario(): Boolean {
        val s = uiState
        uiState = s.copy(
            nameError = Validaciones.validarNombre(s.name),
            emailError = Validaciones.validarEmail(s.email),
            passwordError = Validaciones.validarPasswordRegistro(s.password),
            confirmPasswordError = Validaciones.validarConfirmacion(s.password, s.confirmPassword),
            errorMessage = null
        )
        return !uiState.tieneErrores
    }

    fun onRegisterClick(context: Context, onSuccess: () -> Unit) {
        val state = uiState

        if (!validarFormulario()) return

        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val repository = UsuarioRepository(db.usuarioDao())

            val existingUser = repository.obtenerUsuarioPorEmail(state.email.trim())
            if (existingUser != null) {
                uiState = uiState.copy(emailError = "Este correo ya está registrado.")
            } else {
                val newUser = UsuarioEntity(
                    name = state.name.trim(),
                    email = state.email.trim(),
                    password = state.password
                )
                repository.registrarUsuario(newUser)
                uiState = uiState.copy(errorMessage = null)
                onSuccess()
            }
        }
    }
}
