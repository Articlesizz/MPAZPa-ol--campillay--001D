package com.example.myappnavigation.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myappnavigation.data.database.AppDatabase
import com.example.myappnavigation.data.local.entity.SolicitudEntity
import com.example.myappnavigation.data.repository.SolicitudRepository
import com.example.myappnavigation.model.Validaciones
import kotlinx.coroutines.launch
import java.util.Random

class SolicitudViewModel : ViewModel() {

    var salaEspacio by mutableStateOf("Laboratorio de Computación 1")
    var fechaUso by mutableStateOf("21 de octubre de 2026")
    var horaInicio by mutableStateOf("14:00")
    var horaDevolucion by mutableStateOf("16:00")

    // Errores por campo del formulario de solicitud
    var fechaError by mutableStateOf<String?>(null)
        private set
    var horaInicioError by mutableStateOf<String?>(null)
        private set
    var horaDevolucionError by mutableStateOf<String?>(null)
        private set

    var horasAnticipacion by mutableStateOf(5)
    var cumpleAnticipacion by mutableStateOf(true)

    var solicitudesDocente = mutableStateListOf<SolicitudEntity>()
        private set

    var todasLasSolicitudes = mutableStateListOf<SolicitudEntity>()
        private set

    var showRechazoDialog by mutableStateOf(false)
        private set

    var solicitudAProcesar by mutableStateOf<SolicitudEntity?>(null)
        private set

    var motivoRechazo by mutableStateOf("")

    fun calcularAnticipacion(hora: String) {
        val horaNum = hora.split(":").firstOrNull()?.toIntOrNull() ?: 14
        val horaActualMock = 9
        val diff = horaNum - horaActualMock
        horasAnticipacion = if (diff > 0) diff else 1
        cumpleAnticipacion = horasAnticipacion >= 3
    }

    fun onFechaChanged(valor: String) {
        fechaUso = valor
        fechaError = Validaciones.validarFecha(valor)
    }

    fun onHoraInicioChanged(valor: String) {
        horaInicio = valor
        horaInicioError = Validaciones.validarHora(valor)
        if (horaInicioError == null) calcularAnticipacion(valor)
        if (horaDevolucionError != null) {
            horaDevolucionError = Validaciones.validarHoraDevolucion(valor, horaDevolucion)
        }
    }

    fun onHoraDevolucionChanged(valor: String) {
        horaDevolucion = valor
        horaDevolucionError = Validaciones.validarHoraDevolucion(horaInicio, valor)
    }

    /** Valida todos los campos del formulario. Devuelve true si se puede enviar. */
    fun validarFormulario(): Boolean {
        fechaError = Validaciones.validarFecha(fechaUso)
        horaInicioError = Validaciones.validarHora(horaInicio)
        horaDevolucionError = Validaciones.validarHoraDevolucion(horaInicio, horaDevolucion)
        return fechaError == null && horaInicioError == null && horaDevolucionError == null
    }

    fun enviarSolicitud(
        context: Context,
        docenteEmail: String,
        docenteNombre: String,
        equiposResumen: String,
        onSuccess: () -> Unit
    ) {
        if (!validarFormulario() || !cumpleAnticipacion) return

        val nuevoCodigo = "MP-0" + (100 + Random().nextInt(800))
        val nuevaSolicitud = SolicitudEntity(
            codigoTicket = nuevoCodigo,
            docenteNombre = docenteNombre,
            docenteEmail = docenteEmail,
            salaEspacio = salaEspacio,
            fechaUso = fechaUso,
            horaInicio = horaInicio,
            horaDevolucion = horaDevolucion,
            equiposResumen = equiposResumen,
            horasAnticipacion = horasAnticipacion,
            cumpleAnticipacion = cumpleAnticipacion,
            estadoTicket = "Pendiente",
            fechaCreacion = "21 oct. 2026 - 09:00"
        )

        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val repository = SolicitudRepository(db.solicitudDao())
            repository.crearSolicitud(nuevaSolicitud)
            cargarSolicitudesDocente(context, docenteEmail)
            onSuccess()
        }
    }

    fun cargarSolicitudesDocente(context: Context, email: String) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val repository = SolicitudRepository(db.solicitudDao())
            val list = repository.obtenerSolicitudesDocente(email)
            solicitudesDocente.clear()
            solicitudesDocente.addAll(list)
        }
    }

    fun cargarTodasLasSolicitudes(context: Context) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val repository = SolicitudRepository(db.solicitudDao())
            val list = repository.obtenerTodasLasSolicitudes()
            todasLasSolicitudes.clear()
            todasLasSolicitudes.addAll(list)
        }
    }

    fun aprobarSolicitud(context: Context, solicitud: SolicitudEntity) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val repository = SolicitudRepository(db.solicitudDao())
            repository.responderSolicitud(solicitud.id, "Aceptada", null)
            cargarTodasLasSolicitudes(context)
        }
    }

    fun abrirDialogoRechazo(solicitud: SolicitudEntity) {
        solicitudAProcesar = solicitud
        motivoRechazo = ""
        showRechazoDialog = true
    }

    fun cerrarDialogoRechazo() {
        showRechazoDialog = false
        solicitudAProcesar = null
        motivoRechazo = ""
    }

    fun confirmarRechazo(context: Context) {
        val sol = solicitudAProcesar ?: return
        if (motivoRechazo.isBlank()) return

        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val repository = SolicitudRepository(db.solicitudDao())
            repository.responderSolicitud(sol.id, "Rechazada", motivoRechazo)
            cerrarDialogoRechazo()
            cargarTodasLasSolicitudes(context)
        }
    }
}
