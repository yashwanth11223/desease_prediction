package com.example.healthapp.data

data class HealthRecord(

    val heartRate: Int = 0,

    val oxygen: Int = 0,

    val prediction: String = "",

    val timestamp: Long = 0
)