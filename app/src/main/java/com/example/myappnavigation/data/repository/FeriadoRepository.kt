package com.example.myappnavigation.data.repository

import com.example.myappnavigation.data.remote.RetrofitClient
import com.example.myappnavigation.data.remote.api.FeriadoDto

class FeriadoRepository {

    /** Devuelve el próximo feriado de Chile, o null si no hay conexión. */
    suspend fun obtenerProximoFeriado(): FeriadoDto? {
        return try {
            val response = RetrofitClient.feriadoApi.getProximosFeriados("CL")
            if (response.isSuccessful) response.body()?.firstOrNull() else null
        } catch (e: Exception) {
            null
        }
    }
}
