package com.example.myappnavigation.data

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.BatteryManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log

object NativeResources {

    /**
     * Recurso Nativo 1: Nivel de Batería del Dispositivo (BatteryManager)
     * Vinculación con el caso: Monitoreo de carga del dispositivo de administración
     * y preparación/revisión previa de baterías para equipos del Pañol.
     */
    fun obtenerNivelBateria(context: Context): Int {
        return try {
            val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } catch (e: Exception) {
            Log.e("Bateria", "Error al leer nivel de batería: ${e.message}")
            100
        }
    }

    /**
     * Recurso Nativo 2: Vibración del Sistema (Vibrator)
     * Vinculación con el caso: Retroalimentación física inmediata al confirmar,
     * aprobar o rechazar solicitudes de préstamos de equipos.
     */
    fun ejecutarVibracion(context: Context, duracionMs: Long = 500) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                val vibrator = vibratorManager.defaultVibrator
                vibrator.vibrate(VibrationEffect.createOneShot(duracionMs, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(duracionMs, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(duracionMs)
                }
            }
            Log.d("Vibracion", "Vibración ejecutada por $duracionMs ms")
        } catch (e: Exception) {
            Log.e("Vibracion", "Error al ejecutar vibración: ${e.message}")
        }
    }

    /**
     * Recurso Nativo 3: Linterna del Dispositivo (CameraManager)
     * Vinculación con el caso: Inspección física de equipos e inventarios en
     * estantes oscuros o Racks del Pañol.
     */
    fun alternarLinterna(context: Context, encendido: Boolean) {
        try {
            val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            val cameraId = cameraManager.cameraIdList.firstOrNull { id ->
                val characteristics = cameraManager.getCameraCharacteristics(id)
                characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
            }
            if (cameraId != null) {
                cameraManager.setTorchMode(cameraId, encendido)
                Log.d("Linterna", "Linterna del Pañol ${if (encendido) "ENCENDIDA" else "APAGADA"}")
            }
        } catch (e: Exception) {
            Log.e("Linterna", "Error al alternar linterna: ${e.message}")
        }
    }
}
