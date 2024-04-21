package com.ls.localsky.util.sensors

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import java.lang.ref.WeakReference

class TemperatureSensor private constructor(
    context: Context
): AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE,
    sensorType = Sensor.TYPE_AMBIENT_TEMPERATURE
) {
    companion object{

        //create a weak reference to avoid memory leaks
        private var instanceReference: WeakReference<TemperatureSensor>? = null

        fun getInstance(context: Context): TemperatureSensor{
            val instance = instanceReference?.get()
            return instance?: synchronized(this){
                val newInstance = instanceReference?.get()
                newInstance ?: TemperatureSensor(context).also {
                    instanceReference = WeakReference(it)
                }
            }
        }
    }
}