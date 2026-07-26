package com.example.healthapp.data

import com.google.firebase.database.FirebaseDatabase

object FirebaseHelper {

    private val database =
        FirebaseDatabase.getInstance()

    private val ref =
        database.getReference(
            "health_history"
        )

    fun saveHealthData(

        hr: Int,

        ox: Int,

        prediction: String
    ) {

        val data = mapOf(

            "heartRate" to hr,

            "oxygen" to ox,

            "prediction" to prediction,

            "timestamp" to
                    System.currentTimeMillis()
        )

        ref.push().setValue(data)
    }
}