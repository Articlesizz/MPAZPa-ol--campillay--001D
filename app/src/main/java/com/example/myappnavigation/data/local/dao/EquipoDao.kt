package com.example.myappnavigation.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myappnavigation.data.local.entity.EquipoEntity

@Dao
interface EquipoDao {

    @Query("SELECT * FROM equipos")
    suspend fun getAllEquipos(): List<EquipoEntity>

    @Query("SELECT * FROM equipos WHERE categoria = :categoria")
    suspend fun getEquiposByCategoria(categoria: String): List<EquipoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEquipos(equipos: List<EquipoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEquipo(equipo: EquipoEntity)

    @Update
    suspend fun updateEquipo(equipo: EquipoEntity)

    @Query("SELECT COUNT(*) FROM equipos")
    suspend fun getCount(): Int
}
