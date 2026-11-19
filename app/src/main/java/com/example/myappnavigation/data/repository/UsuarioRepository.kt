package com.example.myappnavigation.data.repository

import com.example.myappnavigation.data.local.dao.UsuarioDao
import com.example.myappnavigation.data.local.entity.UsuarioEntity

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    suspend fun autenticar(email: String, pass: String): UsuarioEntity? {
        val user = usuarioDao.getUserByEmail(email)
        return if (user != null && user.password == pass) {
            user
        } else {
            null
        }
    }

    suspend fun registrarUsuario(user: UsuarioEntity): Boolean {
        return try {
            usuarioDao.registerUser(user)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun obtenerUsuarioPorEmail(email: String): UsuarioEntity? {
        return usuarioDao.getUserByEmail(email)
    }
}
