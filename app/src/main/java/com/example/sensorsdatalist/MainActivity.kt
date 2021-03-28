package com.example.sensorsdatalist

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val adapter by lazy { Adapter() }

    private val rvList by lazy { findViewById<RecyclerView>(R.id.rvList) }

    private val list = mutableListOf<SensorData>()

    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null

    private val listener: SensorEventListener = object : SensorEventListener {

        private var lastSubmitTime = 0L

        override fun onAccuracyChanged(sensor: Sensor?, acc: Int) = Unit

        override fun onSensorChanged(event: SensorEvent) {
            val newSubmitTime = SystemClock.elapsedRealtime()

            if (newSubmitTime - lastSubmitTime > 3_000L) {
                list.add(SensorData(event.values[0], event.values[1], event.values[2]))

                adapter.submitList(list)
                adapter.notifyDataSetChanged()

                lastSubmitTime = newSubmitTime
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        initSensor()
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        sensorManager?.unregisterListener(listener)
        super.onPause()
    }

    private fun initUI() {
        rvList.adapter = adapter
    }

    private fun initSensor() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    }
}
