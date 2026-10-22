package com.example.myappnavigation.ui.screen

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myappnavigation.ui.theme.BeigeAccent
import com.example.myappnavigation.ui.theme.CardBorder
import com.example.myappnavigation.ui.theme.CreamBackground
import com.example.myappnavigation.ui.theme.NavyPrimary
import com.example.myappnavigation.ui.theme.StatusAcceptedText
import com.example.myappnavigation.ui.theme.StatusPendingBg
import com.example.myappnavigation.ui.theme.StatusPendingText
import com.example.myappnavigation.ui.viewmodel.CatalogoViewModel
import com.example.myappnavigation.ui.viewmodel.SolicitudViewModel

@Composable
fun CarritoScreen(
    onNavigateToCatalogo: () -> Unit,
    onNavigateToMisSolicitudes: () -> Unit,
    catalogoViewModel: CatalogoViewModel,
    solicitudViewModel: SolicitudViewModel
) {
    val context = LocalContext.current
    var expandedSala by remember { mutableStateOf(false) }
    val salas = listOf("Laboratorio de Computación 1", "Laboratorio de Computación 2", "Sala 2", "Sala 3", "Sala 4", "Auditorio")

    Scaffold(
        bottomBar = {
            BottomNavBarDocente(
                selectedTab = "Solicitud",
                carritoCount = catalogoViewModel.carritoList.sumOf { it.cantidad },
                onNavigateToCatalogo = onNavigateToCatalogo,
                onNavigateToSolicitud = { },
                onNavigateToMisSolicitudes = onNavigateToMisSolicitudes
            )
        },
        containerColor = CreamBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Header Top Bar
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onNavigateToCatalogo) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás", tint = NavyPrimary)
                }
                Column {
                    Text(
                        text = "DOCENTE / SOLICITANTE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BeigeAccent
                    )
                    Text(
                        text = "Tu solicitud de préstamo",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Card Informativa top
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(CreamBackground),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.EditNote, contentDescription = null, tint = BeigeAccent)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Completá los datos de tu clase",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = NavyPrimary
                        )
                        Text(
                            text = "Hoy: 21 oct. 2026 · 09:00 · Demostración",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección Equipos seleccionados
            val totalUnidades = catalogoViewModel.carritoList.sumOf { it.cantidad }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Equipos seleccionados",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = NavyPrimary
                )
                Text(
                    text = "$totalUnidades unidades",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (catalogoViewModel.carritoList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No has agregado equipos a la solicitud.", color = Color.Gray, fontSize = 14.sp)
                }
            } else {
                catalogoViewModel.carritoList.forEach { item ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
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
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(CreamBackground),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (item.equipo.categoria == "Robótica") Icons.Default.SmartToy else Icons.Default.Computer,
                                        contentDescription = null,
                                        tint = BeigeAccent,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = item.equipo.nombre,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = NavyPrimary
                                    )
                                    Text(
                                        text = "Disponible para solicitar",
                                        fontSize = 11.sp,
                                        color = StatusAcceptedText
                                    )
                                }
                            }

                            // Contador - 1 +
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = { catalogoViewModel.modificarCantidadCarrito(item.equipo.id, -1) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(Icons.Default.Remove, contentDescription = null, tint = NavyPrimary)
                                }
                                Text(
                                    text = "${item.cantidad}",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NavyPrimary,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                                IconButton(
                                    onClick = { catalogoViewModel.modificarCantidadCarrito(item.equipo.id, 1) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = null, tint = NavyPrimary)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Formulario ¿Cuándo y dónde?
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿Cuándo y dónde?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = NavyPrimary
                )
                Text(
                    text = "* Obligatorio",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Sala Selector
            Text("Sala/Espacio *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyPrimary)
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = solicitudViewModel.salaEspacio,
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, modifier = Modifier.clickable { expandedSala = true })
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = BeigeAccent,
                        unfocusedBorderColor = CardBorder
                    ),
                    modifier = Modifier.fillMaxWidth().clickable { expandedSala = true }
                )
                DropdownMenu(
                    expanded = expandedSala,
                    onDismissRequest = { expandedSala = false }
                ) {
                    salas.forEach { s ->
                        DropdownMenuItem(
                            text = { Text(s) },
                            onClick = {
                                solicitudViewModel.salaEspacio = s
                                expandedSala = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Fecha de uso
            Text("Fecha de uso *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyPrimary)
            OutlinedTextField(
                value = solicitudViewModel.fechaUso,
                onValueChange = { solicitudViewModel.fechaUso = it },
                trailingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color.Gray) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = BeigeAccent,
                    unfocusedBorderColor = CardBorder
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Hora Inicio y Hora Devolución
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Hora de inicio *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyPrimary)
                    OutlinedTextField(
                        value = solicitudViewModel.horaInicio,
                        onValueChange = {
                            solicitudViewModel.horaInicio = it
                            solicitudViewModel.calcularAnticipacion(it)
                        },
                        trailingIcon = { Icon(Icons.Default.Schedule, contentDescription = null, tint = Color.Gray) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = BeigeAccent,
                            unfocusedBorderColor = CardBorder
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text("Hora estimada de devolución *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyPrimary)
                    OutlinedTextField(
                        value = solicitudViewModel.horaDevolucion,
                        onValueChange = { solicitudViewModel.horaDevolucion = it },
                        trailingIcon = { Icon(Icons.Default.Schedule, contentDescription = null, tint = Color.Gray) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = BeigeAccent,
                            unfocusedBorderColor = CardBorder
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val haptic = LocalHapticFeedback.current
            val ruleCardBg by animateColorAsState(
                targetValue = if (solicitudViewModel.cumpleAnticipacion) StatusPendingBg else Color(0xFFFFEBEE),
                animationSpec = tween(300)
            )

            // TARJETA AMARILLA/ROJA REGLA DE ANTICIPACIÓN (3 HORAS)
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ruleCardBg),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        if (solicitudViewModel.cumpleAnticipacion) StatusPendingText else Color.Red,
                        RoundedCornerShape(16.dp)
                    )
            ) {
                Row(modifier = Modifier.padding(14.dp)) {
                    Icon(
                        imageVector = if (solicitudViewModel.cumpleAnticipacion) Icons.Default.AccessTime else Icons.Default.Warning,
                        contentDescription = null,
                        tint = if (solicitudViewModel.cumpleAnticipacion) StatusPendingText else Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Regla de Anticipación: Las solicitudes deben realizarse con un mínimo de 3 horas de anticipación",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = NavyPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (solicitudViewModel.cumpleAnticipacion) Icons.Default.Check else Icons.Default.Warning,
                                contentDescription = null,
                                tint = if (solicitudViewModel.cumpleAnticipacion) StatusAcceptedText else Color.Red,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (solicitudViewModel.cumpleAnticipacion)
                                    "✓ Cumple: ${solicitudViewModel.horasAnticipacion} horas de anticipación"
                                else
                                    "✖ No cumple las 3 horas de anticipación requeridas",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (solicitudViewModel.cumpleAnticipacion) StatusAcceptedText else Color.Red
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "El préstamo queda sujeto a la aprobación del Encargado de TI. Podés seguirlo en Mis solicitudes.",
                fontSize = 11.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Enviar Solicitud
            Button(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    if (catalogoViewModel.carritoList.isEmpty()) {
                        Toast.makeText(context, "Agrega al menos un equipo al carrito", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (!solicitudViewModel.cumpleAnticipacion) {
                        Toast.makeText(context, "No cumple la regla de anticipación de 3 horas", Toast.LENGTH_LONG).show()
                        return@Button
                    }

                    val resumen = catalogoViewModel.carritoList.joinToString(", ") { "${it.cantidad} × ${it.equipo.nombre}" }
                    solicitudViewModel.enviarSolicitud(
                        context = context,
                        docenteEmail = "m.lopez@escuela.cl",
                        docenteNombre = "María López",
                        equiposResumen = resumen
                    ) {
                        catalogoViewModel.limpiarCarrito()
                        Toast.makeText(context, "¡Solicitud enviada con éxito!", Toast.LENGTH_SHORT).show()
                        onNavigateToMisSolicitudes()
                    }
                },
                enabled = solicitudViewModel.cumpleAnticipacion && catalogoViewModel.carritoList.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Enviar Solicitud de Préstamo",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
