package com.example.myappnavigation.data.repository

import com.example.myappnavigation.data.local.dao.EquipoDao
import com.example.myappnavigation.data.local.entity.EquipoEntity
import com.example.myappnavigation.data.remote.RetrofitClient

class EquipoRepository(private val equipoDao: EquipoDao) {

    suspend fun obtenerTodosLosEquipos(): List<EquipoEntity> {
        return try {
            val response = RetrofitClient.equipoApi.getEquipos()
            if (response.isSuccessful && response.body() != null) {
                val remoteList = response.body()!!
                equipoDao.deleteAllEquipos()
                if (remoteList.isNotEmpty()) {
                    equipoDao.insertEquipos(remoteList)
                }
                return remoteList
            }
            equipoDao.getAllEquipos()
        } catch (e: Exception) {
            equipoDao.getAllEquipos()
        }
    }

    suspend fun guardarEquipo(equipo: EquipoEntity): Boolean {
        return try {
            val response = RetrofitClient.equipoApi.crearEquipo(equipo)
            if (response.isSuccessful && response.body() != null) {
                equipoDao.insertEquipo(response.body()!!)
                true
            } else {
                equipoDao.insertEquipo(equipo)
                true
            }
        } catch (e: Exception) {
            equipoDao.insertEquipo(equipo)
            true
        }
    }

    suspend fun actualizarEquipo(equipo: EquipoEntity): Boolean {
        return try {
            val response = RetrofitClient.equipoApi.actualizarEquipo(equipo.id, equipo)
            if (response.isSuccessful && response.body() != null) {
                equipoDao.updateEquipo(response.body()!!)
            } else {
                equipoDao.updateEquipo(equipo)
            }
            true
        } catch (e: Exception) {
            equipoDao.updateEquipo(equipo)
            true
        }
    }

    suspend fun eliminarEquipo(id: Int): Boolean {
        return try {
            RetrofitClient.equipoApi.eliminarEquipo(id)
            equipoDao.deleteEquipoById(id)
            true
        } catch (e: Exception) {
            equipoDao.deleteEquipoById(id)
            true
        }
    }
}
