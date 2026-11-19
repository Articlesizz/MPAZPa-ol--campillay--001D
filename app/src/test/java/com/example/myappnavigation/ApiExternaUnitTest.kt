package com.example.myappnavigation

import com.example.myappnavigation.data.remote.api.FeriadoDto
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Pruebas del mapeo de datos de la API externa de feriados (Nager.Date).
 */
class ApiExternaUnitTest {

    @Test
    fun feriado_fechaIso_seMuestraLegible() {
        val feriado = FeriadoDto(date = "2026-12-08", localName = "Inmaculada Concepción", name = "Immaculate Conception")
        assertEquals("8 dic. 2026", feriado.fechaLegible())
    }

    @Test
    fun feriado_fechaMalFormada_retornaTextoOriginal() {
        val feriado = FeriadoDto(date = "sin-fecha", localName = "X", name = "X")
        assertEquals("sin-fecha", feriado.fechaLegible())
    }
}
