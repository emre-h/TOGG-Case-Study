package com.emre.vhalreader

import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.car.hardware.property.CarPropertyManager.CarPropertyEventCallback
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var carPropertyEventCallback: CarPropertyEventCallback
    private lateinit var carPropertyManager: CarPropertyManager
    private lateinit var speedTextView: TextView
    private val REQUEST_CODE_ASK_PERMISSIONS: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        speedTextView = findViewById(R.id.speedTextView)

        carPropertyManager = Car.createCar(this).getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
        carPropertyEventCallback = object : CarPropertyEventCallback {
            override fun onChangeEvent(carPropertyValue: CarPropertyValue<*>) {
                speedTextView.text = "Speed: " + carPropertyValue.value.toString()
            }

            override fun onErrorEvent(propId: Int, zone: Int) {
                Log.d("error", "GEAR_SELECTION: onErrorEvent($propId, $zone)")
            }
        }

        val permissions: MutableList<String> = ArrayList()
        permissions.add(Car.PERMISSION_SPEED)

        if (checkSelfPermission(Car.PERMISSION_SPEED) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions.toTypedArray(), REQUEST_CODE_ASK_PERMISSIONS)
        } else {
            setSpeedToView()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (checkSelfPermission(Car.PERMISSION_SPEED) == PackageManager.PERMISSION_GRANTED) {
                setSpeedToView()
            }
        }
    }

    private fun setSpeedToView() {
        carPropertyManager.registerCallback(carPropertyEventCallback, VehiclePropertyIds.PERF_VEHICLE_SPEED, CarPropertyManager.SENSOR_RATE_NORMAL)
    }

    override fun onDestroy() {
        super.onDestroy()
        carPropertyManager.unregisterCallback(carPropertyEventCallback)
    }
}