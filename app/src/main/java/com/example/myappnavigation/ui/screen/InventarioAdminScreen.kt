package com.example.myappnavigation.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.Switch
import com.example.myappnavigation.data.NativeResources
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Router
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myappnavigation.data.database.AppDatabase
import com.example.myappnavigation.data.local.entity.EquipoEntity
import com.example.myappnavigation.ui.theme.BeigeAccent
import com.example.myappnavigation.ui.theme.CardBorder
import com.example.myappnavigation.ui.theme.CreamBackground
import com.example.myappnavigation.ui.theme.NavyPrimary
import com.example.myappnavigation.ui.theme.StatusAcceptedBg
import com.example.myappnavigation.ui.theme.StatusAcceptedText
import com.example.myappnavigation.ui.viewmodel.CatalogoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun InventarioAdminScreen(
    onNavigateToConsola: () -> Unit,
    onLogout: () -> Unit = {},
    catalogoViewModel: CatalogoViewModel
) {
    val context = LocalContext.current
    var showAddDialog by remember { mutableStateOf(false) }
    var linternaEncendida by remember { mutableStateOf(false) }
    val nivelBateria = remember { NativeResources.obtenerNivelBateria(context) }

    var nuevoCodigo by remember { mutableStateOf("") }
    var nuevoNombre by remember { mutableStateOf("") }
    var nuevaCategoria by remember { mutableStateOf("Notebooks") }
    var nuevoStock by remember { mutableStateOf("5") }
    var nuevaUbicacion by remember { mutableStateOf("Estante B-1") }

    LaunchedEffect(Unit) {
        catalogoViewModel.cargarEquipos(context)
    }

    val totalUnidades = catalogoViewModel.equiposList.sumOf { it.stockTotal }
    val totalDisponibles = catalogoViewModel.equiposList.sumOf { it.stockDisponible }
    val totalTipos = catalogoViewModel.equiposList.size

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = BeigeAccent,
                contentColor = NavyPrimary,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Equipo")
            }
        },
        bottomBar = {
            BottomNavBarAdmin(
                selectedTab = "Inventario",
                onNavigateToConsola = onNavigateToConsola,
                onNavigateToInventario = { },
                onLogout = onLogout
            )
        },
        containerColor = CreamBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Header Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ENCARGADO DE TI",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BeigeAccent
                    )
                    Text(
                        text = "Inventario y ubicación",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(CreamBackground)
                            .border(1.dp, CardBorder, CircleShape)
                            .clickable { onLogout() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("TI", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NavyPrimary)
                    }

                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Cerrar sesión",
                            tint = NavyPrimary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Tarjeta Superior Oscura de Métricas
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = NavyPrimary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "$totalUnidades", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(text = "Unidades totales", fontSize = 11.sp, color = Color.LightGray)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "$totalDisponibles", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(text = "Disponibles", fontSize = 11.sp, color = Color.LightGray)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "$totalTipos", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(text = "Tipos de equipo", fontSize = 11.sp, color = Color.LightGray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // TARJETA DE RECURSOS NATIVOS (Batería + Linterna de Inspección)
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.BatteryChargingFull, contentDescription = null, tint = BeigeAccent, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Batería: $nivelBateria%",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = NavyPrimary
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.FlashOn, contentDescription = null, tint = if (linternaEncendida) Color(0xFFFFB300) else Color.Gray, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Linterna Pañol",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = NavyPrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Switch(
                            checked = linternaEncendida,
                            onCheckedChange = {
                                linternaEncendida = it
                                NativeResources.alternarLinterna(context, it)
                                NativeResources.ejecutarVibracion(context, 200)
                                Toast.makeText(
                                    context,
                                    if (it) "Linterna encendida para inspección" else "Linterna apagada",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Buscador
            OutlinedTextField(
                value = catalogoViewModel.searchQuery,
                onValueChange = { catalogoViewModel.onSearchQueryChanged(it) },
                placeholder = { Text("Buscar por equipo, código o ubicación", fontSize = 13.sp, color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = BeigeAccent,
                    unfocusedBorderColor = CardBorder
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Ubicación Física interna",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = NavyPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Lista de Equipos
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(catalogoViewModel.equiposList) { equipo ->
                    InventarioCardItem(equipo = equipo)
                }
            }
        }
    }

    // DIÁLOGO PARA AGREGAR NUEVO EQUIPO
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Agregar Nuevo Equipo", fontWeight = FontWeight.Bold, color = NavyPrimary) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = nuevoCodigo,
                        onValueChange = { nuevoCodigo = it },
                        label = { Text("Código (ej. NOT-020)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = nuevoNombre,
                        onValueChange = { nuevoNombre = it },
                        label = { Text("Nombre del equipo") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = nuevaCategoria,
                        onValueChange = { nuevaCategoria = it },
                        label = { Text("Categoría (Notebooks, Robótica, etc.)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = nuevoStock,
                        onValueChange = { nuevoStock = it },
                        label = { Text("Stock Total") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = nuevaUbicacion,
                        onValueChange = { nuevaUbicacion = it },
                        label = { Text("Ubicación Física") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val stockInt = nuevoStock.toIntOrNull() ?: 5
                        val nuevo = EquipoEntity(
                            codigo = if (nuevoCodigo.isBlank()) "EQU-00" + (1..99).random() else nuevoCodigo,
                            nombre = if (nuevoNombre.isBlank()) "Nuevo Equipo" else nuevoNombre,
                            categoria = nuevaCategoria,
                            stockTotal = stockInt,
                            stockDisponible = stockInt,
                            estado = "Disponible",
                            ubicacionFisica = nuevaUbicacion
                        )
                        CoroutineScope(Dispatchers.IO).launch {
                            val db = AppDatabase.getDatabase(context)
                            db.equipoDao().insertEquipo(nuevo)
                            catalogoViewModel.cargarEquipos(context)
                        }
                        showAddDialog = false
                        Toast.makeText(context, "Equipo agregado exitosamente", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
                ) {
                    Text("Guardar", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancelar", color = Color.Gray)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(20.dp)
        )
    }
}

@Composable
fun InventarioCardItem(equipo: EquipoEntity) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(CreamBackground),
                        contentAlignment = Alignment.Center
                    ) {
                        val icon = when (equipo.categoria) {
                            "Robótica" -> Icons.Default.SmartToy
                            "Notebooks" -> Icons.Default.Computer
                            "Proyectores" -> Icons.Default.Tv
                            else -> Icons.Default.Router
                        }
                        Icon(icon, contentDescription = null, tint = BeigeAccent, modifier = Modifier.size(22.dp))
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = equipo.nombre,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = NavyPrimary
                        )
                        Text(
                            text = "${equipo.codigo} · ${equipo.categoria}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }

                IconButton(onClick = { }) {
                    Icon(Icons.Default.MoreVert, contentDescription = null, tint = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Stock Total ", fontSize = 12.sp, color = Color.Gray)
                    Text("${equipo.stockTotal}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = NavyPrimary)
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(StatusAcceptedBg)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Stock Disponible ", fontSize = 12.sp, color = StatusAcceptedText)
                        Text("${equipo.stockDisponible}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = StatusAcceptedText)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = BeigeAccent, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = equipo.ubicacionFisica,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
