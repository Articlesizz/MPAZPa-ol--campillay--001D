package com.example.myappnavigation.data.repository

import com.example.myappnavigation.data.local.dao.SolicitudDao
import com.example.myappnavigation.data.local.entity.SolicitudEntity
import com.example.myappnavigation.data.remote.RetrofitClient

class SolicitudRepository(private val solicitudDao: SolicitudDao) {

    suspend fun obtenerSolicitudesDocente(email: String): List<SolicitudEntity> {
        return try {
            val response = RetrofitClient.solicitudApi.getSolicitudesByDocente(email)
            if (response.isSuccessful && response.body() != null) {
                val remoteList = response.body()!!
                remoteList.forEach { solicitudDao.insertSolicitud(it) }
                return remoteList
            }
            solicitudDao.getSolicitudesByDocente(email)
        } catch (e: Exception) {
            solicitudDao.getSolicitudesByDocente(email)
        }
    }

    suspend fun obtenerTodasLasSolicitudes(): List<SolicitudEntity> {
        return try {
            val response = RetrofitClient.solicitudApi.getTodasLasSolicitudes()
            if (response.isSuccessful && response.body() != null) {
                val remoteList = response.body()!!
                remoteList.forEach { solicitudDao.insertSolicitud(it) }
                return remoteList
            }
            solicitudDao.getAllSolicitudes()
        } catch (e: Exception) {
            solicitudDao.getAllSolicitudes()
        }
    }

    suspend fun crearSolicitud(solicitud: SolicitudEntity): Boolean {
        return try {
            val response = RetrofitClient.solicitudApi.crearSolicitud(solicitud)
            if (response.isSuccessful && response.body() != null) {
                solicitudDao.insertSolicitud(response.body()!!)
            } else {
                solicitudDao.insertSolicitud(solicitud)
            }
            true
        } catch (e: Exception) {
            solicitudDao.insertSolicitud(solicitud)
            true
        }
    }

    suspend fun responderSolicitud(id: Int, nuevoEstado: String, justificacion: String?): Boolean {
        return try {
            val response = RetrofitClient.solicitudApi.responderSolicitud(id, nuevoEstado, justificacion)
            if (response.isSuccessful && response.body() != null) {
                solicitudDao.updateSolicitud(response.body()!!)
            } else {
                solicitudDao.responderSolicitud(id, nuevoEstado, justificacion)
            }
            true
        } catch (e: Exception) {
            solicitudDao.responderSolicitud(id, nuevoEstado, justificacion)
            true
        }
    }
}
