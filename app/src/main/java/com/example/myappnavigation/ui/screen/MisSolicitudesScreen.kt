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
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myappnavigation.data.local.entity.SolicitudEntity
import com.example.myappnavigation.ui.theme.BeigeAccent
import com.example.myappnavigation.ui.theme.CardBorder
import com.example.myappnavigation.ui.theme.CreamBackground
import com.example.myappnavigation.ui.theme.NavyPrimary
import com.example.myappnavigation.ui.theme.StatusAcceptedBg
import com.example.myappnavigation.ui.theme.StatusAcceptedText
import com.example.myappnavigation.ui.theme.StatusDeliveredBg
import com.example.myappnavigation.ui.theme.StatusDeliveredText
import com.example.myappnavigation.ui.theme.StatusPendingBg
import com.example.myappnavigation.ui.theme.StatusPendingText
import com.example.myappnavigation.ui.theme.StatusRejectedBg
import com.example.myappnavigation.ui.theme.StatusRejectedText
import com.example.myappnavigation.ui.theme.StatusReturnedBg
import com.example.myappnavigation.ui.theme.StatusReturnedText
import com.example.myappnavigation.ui.viewmodel.CatalogoViewModel
import com.example.myappnavigation.ui.viewmodel.SolicitudViewModel

@Composable
fun MisSolicitudesScreen(
    onNavigateToCatalogo: () -> Unit,
    onNavigateToCarrito: () -> Unit,
    onLogout: () -> Unit = {},
    catalogoViewModel: CatalogoViewModel,
    solicitudViewModel: SolicitudViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        solicitudViewModel.cargarSolicitudesDocente(context, "m.lopez@escuela.cl")
    }

    Scaffold(
        bottomBar = {
            BottomNavBarDocente(
                selectedTab = "Mis solicitudes",
                carritoCount = catalogoViewModel.carritoList.sumOf { it.cantidad },
                onNavigateToCatalogo = onNavigateToCatalogo,
                onNavigateToSolicitud = onNavigateToCarrito,
                onNavigateToMisSolicitudes = { }
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
                        text = "DOCENTE / SOLICITANTE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BeigeAccent
                    )
                    Text(
                        text = "Mis solicitudes",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary
                    )
                }

                // Avatar ML / Logout
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
                        Text("ML", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NavyPrimary)
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

            // Card Banner top
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
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Tu actividad, en un lugar",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = NavyPrimary
                        )
                        Text(
                            text = "${solicitudViewModel.solicitudesDocente.size} solicitudes · ${solicitudViewModel.solicitudesDocente.count { it.estadoTicket == "Pendiente" }} pendiente de aprobación",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(CreamBackground),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.AssignmentTurnedIn, contentDescription = null, tint = BeigeAccent)
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
                    text = "Todas las solicitudes",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = NavyPrimary
                )
                Text(
                    text = "Octubre 2026 ▾",
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
                items(solicitudViewModel.solicitudesDocente) { ticket ->
                    SolicitudTicketCard(ticket = ticket)
                }
            }
        }
    }
}

@Composable
fun SolicitudTicketCard(ticket: SolicitudEntity) {
    val (statusBg, statusText) = when (ticket.estadoTicket) {
        "Pendiente" -> StatusPendingBg to StatusPendingText
        "Aceptada" -> StatusAcceptedBg to StatusAcceptedText
        "Rechazada" -> StatusRejectedBg to StatusRejectedText
        "Entregada" -> StatusDeliveredBg to StatusDeliveredText
        else -> StatusReturnedBg to StatusReturnedText
    }

    val clipboardManager = LocalClipboardManager.current
    val haptic = LocalHapticFeedback.current
    val context = LocalContext.current

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
                    text = ticket.codigoTicket,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = NavyPrimary,
                    modifier = Modifier.clickable {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        clipboardManager.setText(AnnotatedString(ticket.codigoTicket))
                        Toast.makeText(context, "Código ${ticket.codigoTicket} copiado al portapapeles", Toast.LENGTH_SHORT).show()
                    }
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(statusBg)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = ticket.estadoTicket,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusText
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${ticket.fechaUso} · ${ticket.horaInicio}-${ticket.horaDevolucion}",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = ticket.equiposResumen,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = NavyPrimary
            )

            Text(
                text = ticket.salaEspacio,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 2.dp)
            )

            // Si fue RECHAZADA, muestra la caja rosa de Justificación del Pañolero (de los mockups)
            if (ticket.estadoTicket == "Rechazada" && !ticket.justificacionRechazo.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(StatusRejectedBg)
                        .border(1.dp, Color(0xFFFFCDD2), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = "Justificación del Pañolero",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = StatusRejectedText
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = ticket.justificacionRechazo,
                            fontSize = 12.sp,
                            color = Color(0xFF5D4037)
                        )
                    }
                }
            }
        }
    }
}
