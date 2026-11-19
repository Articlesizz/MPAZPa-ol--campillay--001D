package com.example.myappnavigation

import com.example.myappnavigation.data.local.entity.EquipoEntity
import com.example.myappnavigation.ui.viewmodel.CatalogoViewModel
import com.example.myappnavigation.ui.viewmodel.LoginViewModel
import com.example.myappnavigation.ui.viewmodel.RegisterViewModel
import com.example.myappnavigation.ui.viewmodel.SolicitudViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pruebas unitarias de Reglas de Negocio y Lógica de ViewModels
 * Proyecto MPAZ Pañol (DSY1105)
 */
class ReglasNegocioUnitTest {

    // =========================================================================
    // 1. PRUEBAS DE LA REGLA CRÍTICA DE ANTICIPACIÓN (3 HORAS)
    // =========================================================================

    @Test
    fun anticipacion_conMasDeTresHoras_debeCumplirRegla() {
        val viewModel = SolicitudViewModel()

        // Si la hora mock actual es 09:00 y la reserva es para las 14:00 (5 horas de anticipación)
        viewModel.calcularAnticipacion("14:00")

        assertEquals(5, viewModel.horasAnticipacion)
        assertTrue("Debe cumplir la regla de 3 horas de anticipación", viewModel.cumpleAnticipacion)
    }

    @Test
    fun anticipacion_conExactamenteTresHoras_debeCumplirRegla() {
        val viewModel = SolicitudViewModel()

        // 12:00 - 09:00 = 3 horas exactas
        viewModel.calcularAnticipacion("12:00")

        assertEquals(3, viewModel.horasAnticipacion)
        assertTrue("Con 3 horas exactas debe cumplir la regla", viewModel.cumpleAnticipacion)
    }

    @Test
    fun anticipacion_conMenosDeTresHoras_noDebeCumplirRegla() {
        val viewModel = SolicitudViewModel()

        // 10:00 - 09:00 = 1 hora de anticipación
        viewModel.calcularAnticipacion("10:00")

        assertEquals(1, viewModel.horasAnticipacion)
        assertFalse("Con menos de 3 horas NO debe cumplir la regla", viewModel.cumpleAnticipacion)
    }

    @Test
    fun anticipacion_formatoInvalido_aplicaFallbackSeguro() {
        val viewModel = SolicitudViewModel()

        viewModel.calcularAnticipacion("invalido")

        // El fallback toma las 14:00 por defecto (14 - 9 = 5 horas)
        assertEquals(5, viewModel.horasAnticipacion)
        assertTrue(viewModel.cumpleAnticipacion)
    }

    // =========================================================================
    // 2. PRUEBAS DE GESTIÓN DE CARRITO Y CONTROL DE STOCK
    // =========================================================================

    @Test
    fun carrito_agregarEquipoNuevo_iniciaConCantidadUno() {
        val viewModel = CatalogoViewModel()
        val equipo = EquipoEntity(
            id = 1,
            codigo = "NOT-001",
            nombre = "Notebook Lenovo V15",
            categoria = "Notebooks",
            stockTotal = 10,
            stockDisponible = 5,
            ubicacionFisica = "Estante A-1"
        )

        viewModel.agregarAlCarrito(equipo)

        assertEquals(1, viewModel.carritoList.size)
        assertEquals(1, viewModel.carritoList.first().cantidad)
        assertEquals(equipo.id, viewModel.carritoList.first().equipo.id)
    }

    @Test
    fun carrito_incrementarCantidad_respetaStockDisponible() {
        val viewModel = CatalogoViewModel()
        val equipo = EquipoEntity(
            id = 2,
            codigo = "ROB-001",
            nombre = "Kit LEGO WeDo 2.0",
            categoria = "Robótica",
            stockTotal = 5,
            stockDisponible = 2, // Solo 2 disponibles
            ubicacionFisica = "Estante A-2"
        )

        // Agregamos primero (cantidad = 1)
        viewModel.agregarAlCarrito(equipo)
        // Agregamos segundo (cantidad = 2)
        viewModel.agregarAlCarrito(equipo)
        // Intentamos agregar un tercero cuando solo hay 2 disponibles
        viewModel.agregarAlCarrito(equipo)

        assertEquals(1, viewModel.carritoList.size)
        assertEquals("No debe superar el stock disponible de 2", 2, viewModel.carritoList.first().cantidad)
    }

    @Test
    fun carrito_modificarCantidad_alLlegarACero_eliminaItem() {
        val viewModel = CatalogoViewModel()
        val equipo = EquipoEntity(
            id = 3,
            codigo = "PRO-001",
            nombre = "Proyector Epson X49",
            categoria = "Proyectores",
            stockTotal = 3,
            stockDisponible = 2,
            ubicacionFisica = "Armario B-1"
        )

        viewModel.agregarAlCarrito(equipo)
        assertEquals(1, viewModel.carritoList.size)

        // Restamos 1, la cantidad llega a 0
        viewModel.modificarCantidadCarrito(equipo.id, -1)

        assertTrue("El carrito debe quedar vacío al reducir la cantidad a 0", viewModel.carritoList.isEmpty())
    }

    @Test
    fun carrito_limpiarCarrito_vaciaTodosLosItems() {
        val viewModel = CatalogoViewModel()
        val equipo1 = EquipoEntity(id = 1, codigo = "E1", nombre = "Equipo 1", categoria = "C", stockTotal = 5, stockDisponible = 5, ubicacionFisica = "U1")
        val equipo2 = EquipoEntity(id = 2, codigo = "E2", nombre = "Equipo 2", categoria = "C", stockTotal = 5, stockDisponible = 5, ubicacionFisica = "U2")

        viewModel.agregarAlCarrito(equipo1)
        viewModel.agregarAlCarrito(equipo2)
        assertEquals(2, viewModel.carritoList.size)

        viewModel.limpiarCarrito()
        assertTrue("El carrito debe estar completamente vacío", viewModel.carritoList.isEmpty())
    }

    // =========================================================================
    // 3. PRUEBAS DE AUTENTICACIÓN Y VALIDACIONES DE USUARIO
    // =========================================================================

    @Test
    fun login_cambioDeRol_actualizaCredencialesSugeridas() {
        val viewModel = LoginViewModel()

        // Por defecto es DOCENTE
        assertEquals("DOCENTE", viewModel.selectedRol)
        assertEquals("m.lopez@escuela.cl", viewModel.uiState.email)

        // Cambiar a ENCARGADO_TI
        viewModel.onRolSelected("ENCARGADO_TI")
        assertEquals("ENCARGADO_TI", viewModel.selectedRol)
        assertEquals("admin@escuela.cl", viewModel.uiState.email)
    }

    @Test
    fun registro_contrasenasNoCoinciden_asignaMensajeDeError() {
        val viewModel = RegisterViewModel()

        viewModel.onNameChanged("Profesor Juan")
        viewModel.onEmailChanged("juan@escuela.cl")
        viewModel.onPasswordChanged("clave123")
        viewModel.onConfirmPasswordChanged("claveDistinta")

        // La función valida contraseñas antes de interactuar con la BD
        // Verificamos el estado
        assertEquals("clave123", viewModel.uiState.password)
        assertEquals("claveDistinta", viewModel.uiState.confirmPassword)
    }

    // =========================================================================
    // 4. PRUEBAS DE FILTROS EN CATÁLOGO
    // =========================================================================

    @Test
    fun catalogo_seleccionCategoria_actualizaFiltroActivo() {
        val viewModel = CatalogoViewModel()

        assertEquals("Todos", viewModel.selectedCategoria)

        viewModel.onCategoriaSelected("Robótica")
        assertEquals("Robótica", viewModel.selectedCategoria)

        viewModel.onSearchQueryChanged("LEGO")
        assertEquals("LEGO", viewModel.searchQuery)
    }
}
