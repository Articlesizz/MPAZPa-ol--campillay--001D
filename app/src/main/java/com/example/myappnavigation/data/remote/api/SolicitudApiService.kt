package com.example.myappnavigation.data.remote.api

import com.example.myappnavigation.data.local.entity.SolicitudEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SolicitudApiService {

    @GET("api/solicitudes")
    suspend fun getTodasLasSolicitudes(): Response<List<SolicitudEntity>>

    @GET("api/solicitudes/docente")
    suspend fun getSolicitudesByDocente(@Query("email") email: String): Response<List<SolicitudEntity>>

    @POST("api/solicitudes")
    suspend fun crearSolicitud(@Body solicitud: SolicitudEntity): Response<SolicitudEntity>

    @PATCH("api/solicitudes/{id}/estado")
    suspend fun responderSolicitud(
        @Path("id") id: Int,
        @Query("estado") estado: String,
        @Query("justificacion") justificacion: String?
    ): Response<SolicitudEntity>
}
