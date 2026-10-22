package com.example.myappnavigation.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myappnavigation.data.local.dao.EquipoDao
import com.example.myappnavigation.data.local.dao.SolicitudDao
import com.example.myappnavigation.data.local.dao.UsuarioDao
import com.example.myappnavigation.data.local.entity.EquipoEntity
import com.example.myappnavigation.data.local.entity.SolicitudEntity
import com.example.myappnavigation.data.local.entity.UsuarioEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [UsuarioEntity::class, EquipoEntity::class, SolicitudEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun equipoDao(): EquipoDao
    abstract fun solicitudDao(): SolicitudDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mpaz_panol_database.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val context: Context
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateInitialData(database)
                    }
                }
            }
        }

        private suspend fun populateInitialData(db: AppDatabase) {
            // 1. Usuarios iniciales
            val userDao = db.usuarioDao()
            if (userDao.getUserByEmail("m.lopez@escuela.cl") == null) {
                userDao.registerUser(
                    UsuarioEntity(
                        name = "María López",
                        email = "m.lopez@escuela.cl",
                        password = "123",
                        rol = "DOCENTE"
                    )
                )
            }
            if (userDao.getUserByEmail("admin@escuela.cl") == null) {
                userDao.registerUser(
                    UsuarioEntity(
                        name = "Encargado TI",
                        email = "admin@escuela.cl",
                        password = "123",
                        rol = "ENCARGADO_TI"
                    )
                )
            }

            // 2. Equipos iniciales (de los Mockups)
            val equipoDao = db.equipoDao()
            if (equipoDao.getCount() == 0) {
                equipoDao.insertEquipos(
                    listOf(
                        EquipoEntity(
                            codigo = "ROB-001",
                            nombre = "Kit LEGO WeDo 2.0",
                            categoria = "Robótica",
                            stockTotal = 5,
                            stockDisponible = 3,
                            estado = "Disponible",
                            ubicacionFisica = "Estante A-2"
                        ),
                        EquipoEntity(
                            codigo = "NOT-015",
                            nombre = "Notebook Lenovo V15",
                            categoria = "Notebooks",
                            stockTotal = 12,
                            stockDisponible = 8,
                            estado = "Disponible",
                            ubicacionFisica = "Armario B-1"
                        ),
                        EquipoEntity(
                            codigo = "PRO-003",
                            nombre = "Proyector Epson X49",
                            categoria = "Proyectores",
                            stockTotal = 3,
                            stockDisponible = 2,
                            estado = "Disponible",
                            ubicacionFisica = "Estante A-3"
                        ),
                        EquipoEntity(
                            codigo = "RED-004",
                            nombre = "Router TP-Link AX23",
                            categoria = "Redes",
                            stockTotal = 4,
                            stockDisponible = 2,
                            estado = "Disponible",
                            ubicacionFisica = "Rack Principal Redes"
                        )
                    )
                )
            }

            // 3. Solicitudes iniciales (de los Mockups)
            val solicitudDao = db.solicitudDao()
            if (solicitudDao.getCount() == 0) {
                solicitudDao.insertSolicitud(
                    SolicitudEntity(
                        codigoTicket = "MP-0268",
                        docenteNombre = "María López",
                        docenteEmail = "m.lopez@escuela.cl",
                        salaEspacio = "Laboratorio de Computación 1",
                        fechaUso = "21 oct. 2026",
                        horaInicio = "14:00",
                        horaDevolucion = "16:00",
                        equiposResumen = "1 × Kit LEGO WeDo 2.0, 1 × Notebook Lenovo V15",
                        horasAnticipacion = 5,
                        cumpleAnticipacion = true,
                        estadoTicket = "Pendiente",
                        fechaCreacion = "21 oct. 2026 - 09:00"
                    )
                )
                solicitudDao.insertSolicitud(
                    SolicitudEntity(
                        codigoTicket = "MP-0261",
                        docenteNombre = "María López",
                        docenteEmail = "m.lopez@escuela.cl",
                        salaEspacio = "Sala 4",
                        fechaUso = "22 oct. 2026",
                        horaInicio = "10:00",
                        horaDevolucion = "12:00",
                        equiposResumen = "1 × Proyector Epson X49",
                        horasAnticipacion = 5,
                        cumpleAnticipacion = true,
                        estadoTicket = "Aceptada",
                        fechaCreacion = "20 oct. 2026 - 15:30"
                    )
                )
                solicitudDao.insertSolicitud(
                    SolicitudEntity(
                        codigoTicket = "MP-0254",
                        docenteNombre = "María López",
                        docenteEmail = "m.lopez@escuela.cl",
                        salaEspacio = "Laboratorio de Computación 1",
                        fechaUso = "20 oct. 2026",
                        horaInicio = "11:00",
                        horaDevolucion = "13:00",
                        equiposResumen = "2 × Router TP-Link AX23",
                        horasAnticipacion = 5,
                        cumpleAnticipacion = true,
                        estadoTicket = "Rechazada",
                        justificacionRechazo = "Equipos reservados para mantenimiento de la red durante ese horario.",
                        fechaCreacion = "19 oct. 2026 - 08:00"
                    )
                )
                solicitudDao.insertSolicitud(
                    SolicitudEntity(
                        codigoTicket = "MP-0258",
                        docenteNombre = "María López",
                        docenteEmail = "m.lopez@escuela.cl",
                        salaEspacio = "Sala 2",
                        fechaUso = "21 oct. 2026",
                        horaInicio = "08:00",
                        horaDevolucion = "12:00",
                        equiposResumen = "1 × Notebook Lenovo V15",
                        horasAnticipacion = 5,
                        cumpleAnticipacion = true,
                        estadoTicket = "Entregada",
                        fechaCreacion = "20 oct. 2026 - 17:00"
                    )
                )
                solicitudDao.insertSolicitud(
                    SolicitudEntity(
                        codigoTicket = "MP-0246",
                        docenteNombre = "María López",
                        docenteEmail = "m.lopez@escuela.cl",
                        salaEspacio = "Sala 3",
                        fechaUso = "19 oct. 2026",
                        horaInicio = "09:00",
                        horaDevolucion = "11:00",
                        equiposResumen = "1 × Kit LEGO WeDo 2.0",
                        horasAnticipacion = 5,
                        cumpleAnticipacion = true,
                        estadoTicket = "Devuelta",
                        fechaCreacion = "18 oct. 2026 - 12:00"
                    )
                )
            }
        }
    }
}
