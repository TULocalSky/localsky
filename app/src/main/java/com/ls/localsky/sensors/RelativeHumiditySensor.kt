package com.ls.localsky.sensors

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import java.lang.ref.WeakReference

class RelativeHumiditySensor private constructor(
    context: Context
): AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_RELATIVE_HUMIDITY,
    sensorType = Sensor.TYPE_RELATIVE_HUMIDITY
) {
    companion object{

        //create a weak reference to avoid memory leaks
        private var instanceReference: WeakReference<RelativeHumiditySensor>? = null

        fun getInstance(context: Context): RelativeHumiditySensor {
            val instance = instanceReference?.get()
            return instance?: synchronized(this){
                val newInstance = instanceReference?.get()
                newInstance ?: RelativeHumiditySensor(context).also {
                    instanceReference = WeakReference(it)
                }
            }
        }
    }
}