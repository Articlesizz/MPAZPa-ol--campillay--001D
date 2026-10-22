package com.example.myappnavigation.ui.screen

import android.widget.Toast
import androidx.compose.animation.animateContentSize
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myappnavigation.data.local.entity.SolicitudEntity
import com.example.myappnavigation.ui.theme.BeigeAccent
import com.example.myappnavigation.ui.theme.CardBorder
import com.example.myappnavigation.ui.theme.CreamBackground
import com.example.myappnavigation.ui.theme.NavyPrimary
import com.example.myappnavigation.ui.theme.StatusPendingBg
import com.example.myappnavigation.ui.theme.StatusPendingText
import com.example.myappnavigation.ui.theme.StatusRejectedBg
import com.example.myappnavigation.ui.theme.StatusRejectedText
import com.example.myappnavigation.ui.viewmodel.SolicitudViewModel

@Composable
fun ConsolaAdminScreen(
    onNavigateToInventario: () -> Unit,
    onLogout: () -> Unit = {},
    solicitudViewModel: SolicitudViewModel
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf("Pendientes") }

    LaunchedEffect(Unit) {
        solicitudViewModel.cargarTodasLasSolicitudes(context)
    }

    val solicitudesFiltradas = when (selectedTab) {
        "Pendientes" -> solicitudViewModel.todasLasSolicitudes.filter { it.estadoTicket == "Pendiente" }
        "En Préstamo" -> solicitudViewModel.todasLasSolicitudes.filter { it.estadoTicket == "Aceptada" || it.estadoTicket == "Entregada" }
        else -> solicitudViewModel.todasLasSolicitudes.filter { it.estadoTicket == "Rechazada" || it.estadoTicket == "Devuelta" }
    }

    Scaffold(
        bottomBar = {
            BottomNavBarAdmin(
                selectedTab = "Consola",
                onNavigateToConsola = { },
                onNavigateToInventario = onNavigateToInventario,
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
                        text = "Consola de administración",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary
                    )
                }

                // Avatar TI / Logout
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

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Control de préstamos",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = NavyPrimary
            )
            Text(
                text = "21 oct. 2026 - 09:00 - Datos de demostración",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Tabs
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Pendientes", "En Préstamo", "Historial").forEach { tab ->
                    val isSel = selectedTab == tab
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSel) BeigeAccent else Color.White)
                            .border(1.dp, if (isSel) BeigeAccent else CardBorder, RoundedCornerShape(12.dp))
                            .clickable { selectedTab = tab },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tab,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = NavyPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Por revisar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = NavyPrimary
                )
                Text(
                    text = "${solicitudesFiltradas.size} solicitudes",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Lista de solicitudes
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(solicitudesFiltradas) { item ->
                    AdminSolicitudCard(
                        solicitud = item,
                        onAprobar = {
                            solicitudViewModel.aprobarSolicitud(context, item)
                            Toast.makeText(context, "Solicitud Aprobada", Toast.LENGTH_SHORT).show()
                        },
                        onRechazar = {
                            solicitudViewModel.abrirDialogoRechazo(item)
                        }
                    )
                }
            }
        }
    }

    // DIÁLOGO MODAL EMERGENTE DE RECHAZO (Exacto a Figma Image 5)
    if (solicitudViewModel.showRechazoDialog) {
        AlertDialog(
            onDismissRequest = { solicitudViewModel.cerrarDialogoRechazo() },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Comment, contentDescription = null, tint = StatusRejectedText)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Rechazar solicitud",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary
                    )
                }
            },
            text = {
                Column {
                    val sol = solicitudViewModel.solicitudAProcesar
                    Text(
                        text = "${sol?.codigoTicket} · ${sol?.docenteNombre}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary
                    )
                    Text(
                        text = "La justificación será visible para la docente.",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Text(
                        text = "Motivo/Justificación del rechazo *",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = NavyPrimary
                    )

                    OutlinedTextField(
                        value = solicitudViewModel.motivoRechazo,
                        onValueChange = { solicitudViewModel.motivoRechazo = it },
                        placeholder = { Text("Ej. El laboratorio estará cerrado por mantenimiento eléctrico...", fontSize = 12.sp) },
                        minLines = 3,
                        maxLines = 4,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = BeigeAccent,
                            unfocusedBorderColor = CardBorder
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "* Campo obligatorio",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (solicitudViewModel.motivoRechazo.isBlank()) {
                            Toast.makeText(context, "Ingresa un motivo de rechazo", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        solicitudViewModel.confirmarRechazo(context)
                        Toast.makeText(context, "Solicitud Rechazada con justificación", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = StatusRejectedBg),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text("Confirmar Rechazo", color = StatusRejectedText, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { solicitudViewModel.cerrarDialogoRechazo() }) {
                    Text("Cancelar", color = Color.Gray)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(20.dp)
        )
    }
}

@Composable
fun AdminSolicitudCard(
    solicitud: SolicitudEntity,
    onAprobar: () -> Unit,
    onRechazar: () -> Unit
) {
    val haptic = LocalHapticFeedback.current

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = solicitud.codigoTicket,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = NavyPrimary
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(StatusPendingBg)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = solicitud.estadoTicket,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = StatusPendingText
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = solicitud.docenteNombre,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = NavyPrimary
            )

            Text(
                text = "Sala: ${solicitud.salaEspacio}",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Text(
                text = "Horario: ${solicitud.fechaUso} · ${solicitud.horaInicio}-${solicitud.horaDevolucion}",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = solicitud.equiposResumen,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = NavyPrimary
            )

            Spacer(modifier = Modifier.height(14.dp))

            if (solicitud.estadoTicket == "Pendiente") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Botón APROBAR
                    Button(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onAprobar()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("APROBAR", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Botón RECHAZAR
                    OutlinedButton(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onRechazar()
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Clear, contentDescription = null, tint = StatusRejectedText, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("RECHAZAR", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = StatusRejectedText)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavBarAdmin(
    selectedTab: String,
    onNavigateToConsola: () -> Unit,
    onNavigateToInventario: () -> Unit,
    onLogout: () -> Unit = {}
) {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = Icons.Default.Dashboard,
                label = "Consola",
                isSelected = selectedTab == "Consola",
                onClick = onNavigateToConsola
            )

            BottomNavItem(
                icon = Icons.Default.Inventory,
                label = "Inventario",
                isSelected = selectedTab == "Inventario",
                onClick = onNavigateToInventario
            )

            BottomNavItem(
                icon = Icons.Default.Person,
                label = "Cuenta",
                isSelected = selectedTab == "Cuenta",
                onClick = onLogout
            )
        }
    }
}
