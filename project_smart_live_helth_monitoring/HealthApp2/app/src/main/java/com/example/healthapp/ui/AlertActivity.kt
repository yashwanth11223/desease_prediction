package com.example.healthapp.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AlertActivity : AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        val layout =
            LinearLayout(this)

        layout.orientation =
            LinearLayout.VERTICAL

        layout.setPadding(
            40,
            40,
            40,
            40
        )

        val title =
            TextView(this)

        title.text =
            "EMERGENCY ALERT"

        title.textSize = 30f

        title.setTextColor(
            Color.RED
        )

        val msg =
            TextView(this)

        msg.text =
            "Critical health condition detected"

        msg.textSize = 22f

        layout.addView(title)

        layout.addView(msg)

        setContentView(layout)
    }
}