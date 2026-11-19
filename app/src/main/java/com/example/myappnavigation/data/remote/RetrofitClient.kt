package com.example.myappnavigation.data.remote

import com.example.myappnavigation.data.remote.api.EquipoApiService
import com.example.myappnavigation.data.remote.api.FeriadoApiService
import com.example.myappnavigation.data.remote.api.SolicitudApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // Cambiar a la URL base de tu backend Spring Boot desplegado en Cloud o 10.0.2.2 para el emulador Android
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val equipoApi: EquipoApiService by lazy {
        retrofit.create(EquipoApiService::class.java)
    }

    val solicitudApi: SolicitudApiService by lazy {
        retrofit.create(SolicitudApiService::class.java)
    }

    // API externa pública (Nager.Date) para feriados de Chile
    private const val FERIADOS_BASE_URL = "https://date.nager.at/"

    private val retrofitFeriados: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(FERIADOS_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val feriadoApi: FeriadoApiService by lazy {
        retrofitFeriados.create(FeriadoApiService::class.java)
    }
}
