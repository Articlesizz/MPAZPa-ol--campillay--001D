package com.example.myappnavigation.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "equipos")
data class EquipoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val codigo: String,           // ej: "ROB-001"
    val nombre: String,           // ej: "Kit LEGO WeDo 2.0"
    val categoria: String,        // ej: "Robótica", "Notebooks", "Proyectores", "Redes"
    val stockTotal: Int,          // ej: 5
    val stockDisponible: Int,     // ej: 3
    val estado: String = "Disponible", // "Disponible", "En Mantenimiento", "Fuera de Servicio"
    val ubicacionFisica: String   // ej: "Estante A-2", "Armario B-1", "Rack Principal Redes"
)
