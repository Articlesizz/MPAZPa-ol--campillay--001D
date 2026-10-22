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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Router
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.myappnavigation.data.local.entity.EquipoEntity
import com.example.myappnavigation.ui.theme.BeigeAccent
import com.example.myappnavigation.ui.theme.CardBorder
import com.example.myappnavigation.ui.theme.CreamBackground
import com.example.myappnavigation.ui.theme.NavyPrimary
import com.example.myappnavigation.ui.theme.StatusAcceptedBg
import com.example.myappnavigation.ui.theme.StatusAcceptedText
import com.example.myappnavigation.ui.viewmodel.CatalogoViewModel

@Composable
fun CatalogoScreen(
    onNavigateToCarrito: () -> Unit,
    onNavigateToMisSolicitudes: () -> Unit,
    onLogout: () -> Unit = {},
    viewModel: CatalogoViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.cargarEquipos(context)
    }

    val categorias = listOf("Todos", "Notebooks", "Robótica", "Proyectores", "Redes")
    val equiposFiltrados = viewModel.equiposList.filter { equipo ->
        val coincideCat = viewModel.selectedCategoria == "Todos" || equipo.categoria == viewModel.selectedCategoria
        val coincideQuery = viewModel.searchQuery.isEmpty() || equipo.nombre.contains(viewModel.searchQuery, ignoreCase = true)
        coincideCat && coincideQuery
    }

    Scaffold(
        bottomBar = {
            BottomNavBarDocente(
                selectedTab = "Catálogo",
                carritoCount = viewModel.carritoList.sumOf { it.cantidad },
                onNavigateToCatalogo = { },
                onNavigateToSolicitud = onNavigateToCarrito,
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
                        text = "MPAZ Pañol - Catálogo",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BadgedBox(
                        badge = {
                            val count = viewModel.carritoList.sumOf { it.cantidad }
                            if (count > 0) {
                                Badge(containerColor = BeigeAccent, contentColor = NavyPrimary) {
                                    Text(text = "$count", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .border(1.dp, CardBorder, CircleShape)
                                .clickable { onNavigateToCarrito() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingBag,
                                contentDescription = "Carrito",
                                tint = NavyPrimary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .border(1.dp, CardBorder, CircleShape)
                            .clickable { onLogout() },
                        contentAlignment = Alignment.Center
                    ) {
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

            // Saludo
            Text(
                text = "Hola, María",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = NavyPrimary
            )
            Text(
                text = "¿Qué necesitás para tu próxima clase?",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
            )

            // Buscador
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = { viewModel.onSearchQueryChanged(it) },
                placeholder = { Text("Buscar equipos tecnológicos", fontSize = 14.sp, color = Color.Gray) },
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

            Spacer(modifier = Modifier.height(12.dp))

            // Filtros de Categoría
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categorias) { cat ->
                    val isSelected = viewModel.selectedCategoria == cat
                    Box(
                        modifier = Modifier
                            .height(36.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(if (isSelected) BeigeAccent else Color.White)
                            .border(1.dp, if (isSelected) BeigeAccent else CardBorder, RoundedCornerShape(18.dp))
                            .clickable { viewModel.onCategoriaSelected(cat) }
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (isSelected) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = NavyPrimary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                            Text(
                                text = cat,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = NavyPrimary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Disponibilidad actualizada - 21 oct., 09:00",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Equipos para tu clase",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = NavyPrimary
                )
                Text(
                    text = "${equiposFiltrados.size} equipos",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Lista de Equipos
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(equiposFiltrados) { equipo ->
                    EquipoCardItem(
                        equipo = equipo,
                        onAgregar = {
                            viewModel.agregarAlCarrito(equipo)
                            Toast.makeText(context, "${equipo.nombre} añadido a la solicitud", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EquipoCardItem(
    equipo: EquipoEntity,
    onAgregar: () -> Unit
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Icono según categoría
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(CreamBackground),
                    contentAlignment = Alignment.Center
                ) {
                    val icon = when (equipo.categoria) {
                        "Robótica" -> Icons.Default.SmartToy
                        "Notebooks" -> Icons.Default.Computer
                        "Proyectores" -> Icons.Default.Tv
                        else -> Icons.Default.Router
                    }
                    Icon(imageVector = icon, contentDescription = null, tint = BeigeAccent, modifier = Modifier.size(26.dp))
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = equipo.categoria.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = BeigeAccent
                    )
                    Text(
                        text = equipo.nombre,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary
                    )
                    Text(
                        text = "${equipo.stockDisponible} de ${equipo.stockTotal} disponibles",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Badge Disponibilidad
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(StatusAcceptedBg)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = StatusAcceptedText, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = equipo.estado,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = StatusAcceptedText
                        )
                    }
                }

                // Botón Añadir a Solicitud
                Button(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onAgregar()
                    },
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BeigeAccent)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = NavyPrimary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Añadir a Solicitud",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = NavyPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavBarDocente(
    selectedTab: String,
    carritoCount: Int = 0,
    onNavigateToCatalogo: () -> Unit,
    onNavigateToSolicitud: () -> Unit,
    onNavigateToMisSolicitudes: () -> Unit
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
            // Tab Catálogo
            BottomNavItem(
                icon = Icons.Default.GridOn,
                label = "Catálogo",
                isSelected = selectedTab == "Catálogo",
                onClick = onNavigateToCatalogo
            )

            // Tab Solicitud (Carrito)
            BottomNavItem(
                icon = Icons.Default.ShoppingBag,
                label = "Solicitud",
                isSelected = selectedTab == "Solicitud",
                badgeCount = carritoCount,
                onClick = onNavigateToSolicitud
            )

            // Tab Mis Solicitudes
            BottomNavItem(
                icon = Icons.Default.ListAlt,
                label = "Mis solicitudes",
                isSelected = selectedTab == "Mis solicitudes",
                onClick = onNavigateToMisSolicitudes
            )
        }
    }
}

@Composable
fun BottomNavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    badgeCount: Int = 0,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        BadgedBox(
            badge = {
                if (badgeCount > 0) {
                    Badge(containerColor = BeigeAccent, contentColor = NavyPrimary) {
                        Text(text = "$badgeCount", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(if (isSelected) BeigeAccent else Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = NavyPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = NavyPrimary
        )
    }
}
