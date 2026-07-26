package com.example.healthapp.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.healthapp.R
import com.example.healthapp.data.HealthRecord
import com.google.firebase.database.*

class FirebaseHistoryActivity :
    AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_firebase_history
        )

        val historyText =
            findViewById<TextView>(
                R.id.firebaseHistoryText
            )

        val ref =
            FirebaseDatabase
                .getInstance()
                .getReference(
                    "health_history"
                )

        ref.addValueEventListener(

            object : ValueEventListener {

                override fun onDataChange(
                    snapshot: DataSnapshot
                ) {

                    var text = ""

                    for (child in snapshot.children) {

                        val record =
                            child.getValue(
                                HealthRecord::class.java
                            )

                        if (record != null) {

                            text +=
                                "HR: ${record.heartRate}\n"

                            text +=
                                "O2: ${record.oxygen}\n"

                            text +=
                                "Prediction: ${record.prediction}\n\n"
                        }
                    }

                    historyText.text = text
                }

                override fun onCancelled(
                    error: DatabaseError
                ) {

                }
            }
        )
    }
}