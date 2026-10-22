package com.example.myappnavigation.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myappnavigation.data.local.entity.SolicitudEntity

@Dao
interface SolicitudDao {

    @Query("SELECT * FROM solicitudes ORDER BY id DESC")
    suspend fun getAllSolicitudes(): List<SolicitudEntity>

    @Query("SELECT * FROM solicitudes WHERE docenteEmail = :email ORDER BY id DESC")
    suspend fun getSolicitudesByDocente(email: String): List<SolicitudEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSolicitud(solicitud: SolicitudEntity)

    @Update
    suspend fun updateSolicitud(solicitud: SolicitudEntity)

    @Query("UPDATE solicitudes SET estadoTicket = :nuevoEstado, justificacionRechazo = :justificacion WHERE id = :id")
    suspend fun responderSolicitud(id: Int, nuevoEstado: String, justificacion: String?)

    @Query("SELECT COUNT(*) FROM solicitudes")
    suspend fun getCount(): Int
}
