package com.example.myappnavigation.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * DTO de la API externa pública Nager.Date (https://date.nager.at).
 * Solo se mapean los campos que usa la app.
 */
data class FeriadoDto(
    val date: String,       // ej. "2026-12-08"
    val localName: String,  // ej. "Inmaculada Concepción"
    val name: String        // ej. "Immaculate Conception"
) {
    /** Convierte "2026-12-08" en "8 dic. 2026" sin depender de java.time (minSdk 23). */
    fun fechaLegible(): String {
        val meses = listOf("ene.", "feb.", "mar.", "abr.", "may.", "jun.", "jul.", "ago.", "sept.", "oct.", "nov.", "dic.")
        val partes = date.split("-")
        if (partes.size != 3) return date
        val mes = partes[1].toIntOrNull() ?: return date
        val dia = partes[2].toIntOrNull() ?: return date
        if (mes !in 1..12) return date
        return "$dia ${meses[mes - 1]} ${partes[0]}"
    }
}

/**
 * API externa (no desarrollada por el equipo): feriados oficiales por país.
 * Se usa para avisar al docente del próximo día en que el pañol estará cerrado.
 */
interface FeriadoApiService {

    @GET("api/v3/NextPublicHolidays/{countryCode}")
    suspend fun getProximosFeriados(@Path("countryCode") countryCode: String): Response<List<FeriadoDto>>
}
