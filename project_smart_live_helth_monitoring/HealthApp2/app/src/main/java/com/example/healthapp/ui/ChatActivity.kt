package com.example.healthapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthapp.R
import com.example.healthapp.adapter.ChatAdapter
import com.example.healthapp.api.ApiClient
import com.example.healthapp.model.ChatMessage
import com.google.android.material.textfield.TextInputEditText
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatAdapter
    private lateinit var edtMessage: TextInputEditText
    private lateinit var btnSend: ImageButton
    private lateinit var typingLayout: LinearLayout

    private val messages = mutableListOf<ChatMessage>()

    private lateinit var dot1: TextView
    private lateinit var dot2: TextView
    private lateinit var dot3: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        dot1 = findViewById(R.id.dot1)
        dot2 = findViewById(R.id.dot2)
        dot3 = findViewById(R.id.dot3)
        recyclerView = findViewById(R.id.chatRecyclerView)
        edtMessage = findViewById(R.id.edtMessage)
        btnSend = findViewById(R.id.btnSend)
        typingLayout = findViewById(R.id.typingLayout)

        adapter = ChatAdapter(messages)

        val manager = LinearLayoutManager(this)
        manager.stackFromEnd = true

        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter

        recyclerView.itemAnimator?.apply {
            addDuration = 250
            removeDuration = 250
            moveDuration = 250
        }

        btnSend.setOnClickListener {

            val text = edtMessage.text.toString().trim()

            if (text.isEmpty()) return@setOnClickListener

            addUserMessage(text)

            edtMessage.setText("")

            typingLayout.visibility = View.VISIBLE
            startTypingAnimation()

            sendToAI(text)
        }
    }

    private fun addUserMessage(message: String) {

        messages.add(ChatMessage(message, true))

        adapter.notifyItemInserted(messages.size - 1)

        recyclerView.scrollToPosition(messages.size - 1)
    }

    private fun addBotMessage(message: String) {

        stopTypingAnimation()
        typingLayout.visibility = View.GONE

        messages.add(ChatMessage(message, false))

        adapter.notifyItemInserted(messages.size - 1)

        recyclerView.scrollToPosition(messages.size - 1)
    }

    private fun startTypingAnimation() {

        val anim1 = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val anim2 = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val anim3 = AnimationUtils.loadAnimation(this, R.anim.bounce)

        anim2.startOffset = 200
        anim3.startOffset = 400

        anim1.repeatCount = Animation.INFINITE
        anim2.repeatCount = Animation.INFINITE
        anim3.repeatCount = Animation.INFINITE

        dot1.startAnimation(anim1)
        dot2.startAnimation(anim2)
        dot3.startAnimation(anim3)
    }
    private fun stopTypingAnimation() {

        dot1.clearAnimation()
        dot2.clearAnimation()
        dot3.clearAnimation()

    }
    private fun sendToAI(message: String) {

        ApiClient.sendChat(
            message = message,
            heartRate = 76,
            oxygen = 98,
            prediction = "Healthy"
        ) { reply ->

            runOnUiThread {

                addBotMessage(reply)

            }

        }
    }
}