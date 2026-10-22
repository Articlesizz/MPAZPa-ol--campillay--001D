package com.example.myappnavigation.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myappnavigation.data.database.AppDatabase
import com.example.myappnavigation.data.local.entity.EquipoEntity
import kotlinx.coroutines.launch

data class CarritoItem(
    val equipo: EquipoEntity,
    var cantidad: Int = 1
)

class CatalogoViewModel : ViewModel() {

    var selectedCategoria by mutableStateOf("Todos")
        private set

    var searchQuery by mutableStateOf("")
        private set

    var equiposList = mutableStateListOf<EquipoEntity>()
        private set

    var carritoList = mutableStateListOf<CarritoItem>()
        private set

    fun cargarEquipos(context: Context) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val list = db.equipoDao().getAllEquipos()
            equiposList.clear()
            equiposList.addAll(list)
        }
    }

    fun onCategoriaSelected(categoria: String) {
        selectedCategoria = categoria
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery = query
    }

    fun agregarAlCarrito(equipo: EquipoEntity) {
        val existingIndex = carritoList.indexOfFirst { it.equipo.id == equipo.id }
        if (existingIndex >= 0) {
            val current = carritoList[existingIndex]
            if (current.cantidad < equipo.stockDisponible) {
                carritoList[existingIndex] = current.copy(cantidad = current.cantidad + 1)
            }
        } else {
            carritoList.add(CarritoItem(equipo = equipo, cantidad = 1))
        }
    }

    fun modificarCantidadCarrito(equipoId: Int, delta: Int) {
        val index = carritoList.indexOfFirst { it.equipo.id == equipoId }
        if (index >= 0) {
            val current = carritoList[index]
            val nuevaCantidad = current.cantidad + delta
            if (nuevaCantidad <= 0) {
                carritoList.removeAt(index)
            } else if (nuevaCantidad <= current.equipo.stockDisponible) {
                carritoList[index] = current.copy(cantidad = nuevaCantidad)
            }
        }
    }

    fun limpiarCarrito() {
        carritoList.clear()
    }
}
