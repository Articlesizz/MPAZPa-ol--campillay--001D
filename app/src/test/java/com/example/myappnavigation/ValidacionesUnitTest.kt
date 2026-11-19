package com.example.myappnavigation

import com.example.myappnavigation.model.Validaciones
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Pruebas unitarias de las validaciones de formularios (retroalimentación por campo).
 */
class ValidacionesUnitTest {

    @Test
    fun email_vacio_retornaObligatorio() {
        assertEquals("El correo es obligatorio.", Validaciones.validarEmail(""))
    }

    @Test
    fun email_sinFormato_retornaError() {
        assertNotNull(Validaciones.validarEmail("profesor.escuela.cl"))
        assertNotNull(Validaciones.validarEmail("profesor@escuela"))
    }

    @Test
    fun email_valido_noRetornaError() {
        assertNull(Validaciones.validarEmail("m.lopez@escuela.cl"))
    }

    @Test
    fun nombre_conNumeros_retornaError() {
        assertNotNull(Validaciones.validarNombre("Juan123"))
    }

    @Test
    fun nombre_valido_conTildes_noRetornaError() {
        assertNull(Validaciones.validarNombre("María López"))
    }

    @Test
    fun passwordRegistro_corta_retornaError() {
        assertNotNull(Validaciones.validarPasswordRegistro("a1"))
    }

    @Test
    fun passwordRegistro_soloLetras_retornaError() {
        assertEquals("Debe combinar letras y números.", Validaciones.validarPasswordRegistro("abcdefg"))
    }

    @Test
    fun passwordRegistro_valida_noRetornaError() {
        assertNull(Validaciones.validarPasswordRegistro("clave123"))
    }

    @Test
    fun confirmacion_distinta_retornaNoCoinciden() {
        assertEquals("Las contraseñas no coinciden.", Validaciones.validarConfirmacion("clave123", "clave124"))
    }

    @Test
    fun hora_formatoInvalido_retornaError() {
        assertNotNull(Validaciones.validarHora("25:00"))
        assertNotNull(Validaciones.validarHora("2pm"))
        assertNull(Validaciones.validarHora("14:30"))
    }

    @Test
    fun horaDevolucion_anteriorAInicio_retornaError() {
        assertEquals(
            "Debe ser posterior a la hora de inicio.",
            Validaciones.validarHoraDevolucion("14:00", "13:00")
        )
        assertNull(Validaciones.validarHoraDevolucion("14:00", "16:00"))
    }

    @Test
    fun fecha_vacia_retornaObligatoria() {
        assertNotNull(Validaciones.validarFecha("  "))
    }
}
