/**
 * this is a high level abstraction to handle any sensor input/output
 * not android specific
 *  **/

package com.ls.localsky.util.sensors

abstract class MeasurableSensor(
    protected val sensorType: Int
) {

    /*
    * we want to accept values from all types of sensors
    * some sensors send more values than others
    *
    * e.g. the gyroscope sends 3 values whereas the light sensor only returns 1
    *
    * so we take any values returned and put them into a list
    * */
    protected var onSesorValueChanged: ((List<Float>) -> Unit)? = null

    abstract val doesSensorExist: Boolean   //does the device have the sensor you request

    abstract fun startListening()   //fun to take in values from a particular sensor
    abstract fun stopListening()    //stop taking in values

    fun setOnSensorValuesChangedListener(listener: (List<Float>) -> Unit){
        onSesorValueChanged = listener
    }
}