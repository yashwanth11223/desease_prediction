package com.example.healthapp.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.healthapp.data.HistoryManager

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        val textView =
            TextView(this)

        textView.textSize = 18f

        if (
            HistoryManager.historyList.isEmpty()
        ) {

            textView.text =
                "No History Available"

        } else {

            textView.text =
                HistoryManager.historyList.joinToString(
                    separator = "\n\n"
                )
        }

        setContentView(textView)
    }
}