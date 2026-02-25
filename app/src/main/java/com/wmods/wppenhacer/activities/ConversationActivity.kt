package com.wmods.wppenhacer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wmods.wppenhacer.R
import android.widget.EditText
import android.widget.Button
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView

class ConversationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation)
        val rv = findViewById<RecyclerView>(R.id.recycler_messages)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = MessageAdapter()
        findViewById<Button>(R.id.btn_send).setOnClickListener {
            // placeholder - implement Gmail send when ready
        }
    }

    class MessageAdapter: RecyclerView.Adapter<MessageAdapter.VH>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
            return VH(v)
        }

        override fun getItemCount() = 10

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.bind(if (position%2==0) "Incoming message #$position" else "You: reply #$position")
        }

        class VH(view: View): RecyclerView.ViewHolder(view) {
            val body = view.findViewById<TextView>(R.id.message_body)
            fun bind(text:String) { body.text = text }
        }
    }
}
