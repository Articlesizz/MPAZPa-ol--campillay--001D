package com.example.myappnavigation.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myappnavigation.data.local.entity.UsuarioEntity

@Dao
interface UsuarioDao {

    // registrar un nuevo usuario en la base de datos sqlite
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registerUser(user: UsuarioEntity)

    // busca un usuario por correo
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UsuarioEntity?
}