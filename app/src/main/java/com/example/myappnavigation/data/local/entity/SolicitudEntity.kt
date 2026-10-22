package com.example.myappnavigation.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "solicitudes")
data class SolicitudEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val codigoTicket: String,          // ej. "MP-0268"
    val docenteNombre: String,         // ej. "María López"
    val docenteEmail: String,
    val salaEspacio: String,           // ej. "Laboratorio de Computación 1"
    val fechaUso: String,              // ej. "21 oct. 2026"
    val horaInicio: String,            // ej. "14:00"
    val horaDevolucion: String,        // ej. "16:00"
    val equiposResumen: String,        // ej. "1 × Kit LEGO WeDo 2.0, 1 × Notebook Lenovo V15"
    val horasAnticipacion: Int = 5,
    val cumpleAnticipacion: Boolean = true,
    val estadoTicket: String = "Pendiente", // "Pendiente", "Aceptada", "Rechazada", "Entregada", "Devuelta"
    val justificacionRechazo: String? = null,
    val fechaCreacion: String = ""
)
