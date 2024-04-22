/**
 * mid level abstraction for android specific sensor
 * **/

package com.ls.localsky.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

abstract class AndroidSensor(
    private val context: Context,       //used to get an instance to the SensorManager
    private val sensorFeature: String,  //used to determine if a specific sensor exists
    sensorType: Int                     //the sensor we want to use
): MeasurableSensor(sensorType), SensorEventListener {

    override val doesSensorExist: Boolean
        get() = context.packageManager.hasSystemFeature(sensorFeature)

    private lateinit var sensorManager: SensorManager   //android system service reporting sensor values
    private var sensor: Sensor? = null                  //returned from SensorManager after it starts listening

    override fun startListening() {
        if(!doesSensorExist) return

        //if this is the first time calling this function
        if(!::sensorManager.isInitialized && sensor == null){
            sensorManager = context.getSystemService(SensorManager::class.java) as SensorManager
            sensor = sensorManager.getDefaultSensor(sensorType)
        }
        sensor.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun stopListening() {
        if(!doesSensorExist || !::sensorManager.isInitialized) return

        sensor?.let {
            sensorManager.unregisterListener(this)
        }
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        if(!doesSensorExist) return

        //only trigger for sensors we requested
        if(sensorEvent?.sensor?.type == sensorType){
            onSesorValueChanged?.invoke(sensorEvent.values.toList())
        }
    }

    //only really need if we care about the accuracy of the sensor
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) = Unit
}