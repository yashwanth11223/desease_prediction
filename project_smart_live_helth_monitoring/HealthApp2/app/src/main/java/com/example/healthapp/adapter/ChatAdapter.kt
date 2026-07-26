package com.example.healthapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthapp.R
import com.example.healthapp.model.ChatMessage

class ChatAdapter(
    private val messages: MutableList<ChatMessage>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val USER = 1
        private const val BOT = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isUser) USER else BOT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == USER) {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user_message, parent, false)

            UserViewHolder(view)

        } else {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bot_message, parent, false)

            BotViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val chat = messages[position]

        if (holder is UserViewHolder) {
            holder.message.text = chat.message
        }

        if (holder is BotViewHolder) {
            holder.message.text = chat.message
        }
    }

    override fun getItemCount(): Int = messages.size

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val message: TextView = itemView.findViewById(R.id.txtMessage)
    }

    class BotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val message: TextView = itemView.findViewById(R.id.txtMessage)
    }
}