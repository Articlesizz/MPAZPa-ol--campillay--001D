package com.example.myappnavigation.data.remote.api

import com.example.myappnavigation.data.local.entity.EquipoEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EquipoApiService {

    @GET("api/equipos")
    suspend fun getEquipos(): Response<List<EquipoEntity>>

    @GET("api/equipos/{id}")
    suspend fun getEquipoById(@Path("id") id: Int): Response<EquipoEntity>

    @POST("api/equipos")
    suspend fun crearEquipo(@Body equipo: EquipoEntity): Response<EquipoEntity>

    @PUT("api/equipos/{id}")
    suspend fun actualizarEquipo(@Path("id") id: Int, @Body equipo: EquipoEntity): Response<EquipoEntity>

    @DELETE("api/equipos/{id}")
    suspend fun eliminarEquipo(@Path("id") id: Int): Response<Void>
}
