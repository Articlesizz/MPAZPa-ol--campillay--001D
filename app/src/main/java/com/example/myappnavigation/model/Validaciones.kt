package com.example.myappnavigation.model

/**
 * Reglas de validación de formularios (Login, Registro y Solicitud de préstamo).
 *
 * Cada función devuelve el mensaje de error que se muestra bajo el campo,
 * o null si el valor es válido. Al no depender de Android, se puede
 * probar con pruebas unitarias JVM.
 */
object Validaciones {

    private val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private val HORA_REGEX = Regex("^([01]\\d|2[0-3]):[0-5]\\d$")
    private val NOMBRE_REGEX = Regex("^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ ]+$")

    const val PASSWORD_MIN = 6

    fun validarEmail(email: String): String? = when {
        email.isBlank() -> "El correo es obligatorio."
        !EMAIL_REGEX.matches(email.trim()) -> "Formato de correo inválido (ej. nombre@escuela.cl)."
        else -> null
    }

    fun validarPasswordLogin(password: String): String? =
        if (password.isBlank()) "La contraseña es obligatoria." else null

    fun validarNombre(nombre: String): String? = when {
        nombre.isBlank() -> "El nombre es obligatorio."
        nombre.trim().length < 3 -> "El nombre debe tener al menos 3 caracteres."
        !NOMBRE_REGEX.matches(nombre.trim()) -> "El nombre solo puede contener letras y espacios."
        else -> null
    }

    fun validarPasswordRegistro(password: String): String? = when {
        password.isBlank() -> "La contraseña es obligatoria."
        password.length < PASSWORD_MIN -> "Debe tener al menos $PASSWORD_MIN caracteres."
        !password.any { it.isLetter() } || !password.any { it.isDigit() } ->
            "Debe combinar letras y números."
        else -> null
    }

    fun validarConfirmacion(password: String, confirmacion: String): String? = when {
        confirmacion.isBlank() -> "Debes repetir la contraseña."
        password != confirmacion -> "Las contraseñas no coinciden."
        else -> null
    }

    fun validarFecha(fecha: String): String? =
        if (fecha.isBlank()) "La fecha de uso es obligatoria." else null

    fun validarHora(hora: String): String? = when {
        hora.isBlank() -> "La hora es obligatoria."
        !HORA_REGEX.matches(hora.trim()) -> "Usa el formato HH:mm (ej. 14:00)."
        else -> null
    }

    fun validarHoraDevolucion(horaInicio: String, horaDevolucion: String): String? {
        validarHora(horaDevolucion)?.let { return it }
        if (validarHora(horaInicio) != null) return null
        return if (aMinutos(horaDevolucion) <= aMinutos(horaInicio)) {
            "Debe ser posterior a la hora de inicio."
        } else null
    }

    private fun aMinutos(hora: String): Int {
        val (h, m) = hora.trim().split(":").map { it.toInt() }
        return h * 60 + m
    }
}
