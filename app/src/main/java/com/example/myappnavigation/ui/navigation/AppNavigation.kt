package com.example.myappnavigation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myappnavigation.ui.screen.CarritoScreen
import com.example.myappnavigation.ui.screen.CatalogoScreen
import com.example.myappnavigation.ui.screen.ConsolaAdminScreen
import com.example.myappnavigation.ui.screen.InventarioAdminScreen
import com.example.myappnavigation.ui.screen.Login
import com.example.myappnavigation.ui.screen.MisSolicitudesScreen
import com.example.myappnavigation.ui.screen.Register
import com.example.myappnavigation.ui.viewmodel.CatalogoViewModel
import com.example.myappnavigation.ui.viewmodel.SolicitudViewModel

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val CATALOGO = "catalogo"
    const val CARRITO = "carrito"
    const val MIS_SOLICITUDES = "mis_solicitudes"
    const val CONSOLA_ADMIN = "consola_admin"
    const val INVENTARIO_ADMIN = "inventario_admin"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // ViewModels compartidos
    val catalogoViewModel: CatalogoViewModel = viewModel()
    val solicitudViewModel: SolicitudViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            Login(
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                },
                onLoginSuccess = { rol ->
                    if (rol == "ENCARGADO_TI") {
                        navController.navigate(Routes.CONSOLA_ADMIN) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Routes.CATALOGO) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Routes.REGISTER) {
            Register(
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // --- RUTAS DOCENTE ---
        composable(Routes.CATALOGO) {
            CatalogoScreen(
                onNavigateToCarrito = { navController.navigate(Routes.CARRITO) },
                onNavigateToMisSolicitudes = { navController.navigate(Routes.MIS_SOLICITUDES) },
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                viewModel = catalogoViewModel
            )
        }

        composable(Routes.CARRITO) {
            CarritoScreen(
                onNavigateToCatalogo = { navController.navigate(Routes.CATALOGO) },
                onNavigateToMisSolicitudes = { navController.navigate(Routes.MIS_SOLICITUDES) },
                catalogoViewModel = catalogoViewModel,
                solicitudViewModel = solicitudViewModel
            )
        }

        composable(Routes.MIS_SOLICITUDES) {
            MisSolicitudesScreen(
                onNavigateToCatalogo = { navController.navigate(Routes.CATALOGO) },
                onNavigateToCarrito = { navController.navigate(Routes.CARRITO) },
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                catalogoViewModel = catalogoViewModel,
                solicitudViewModel = solicitudViewModel
            )
        }

        // --- RUTAS ADMINISTRADOR TI ---
        composable(Routes.CONSOLA_ADMIN) {
            ConsolaAdminScreen(
                onNavigateToInventario = { navController.navigate(Routes.INVENTARIO_ADMIN) },
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                solicitudViewModel = solicitudViewModel
            )
        }

        composable(Routes.INVENTARIO_ADMIN) {
            InventarioAdminScreen(
                onNavigateToConsola = { navController.navigate(Routes.CONSOLA_ADMIN) },
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                catalogoViewModel = catalogoViewModel
            )
        }
    }
}
