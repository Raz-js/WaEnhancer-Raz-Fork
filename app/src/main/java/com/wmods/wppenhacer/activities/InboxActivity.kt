package com.wmods.wppenhacer.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wmods.wppenhacer.R
import android.widget.TextView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

class InboxActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inbox)
        val rv = findViewById<RecyclerView>(R.id.recycler_threads)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = ThreadAdapter {
            startActivity(Intent(this, ConversationActivity::class.java))
        }
    }

    class ThreadAdapter(val click: ()->Unit): RecyclerView.Adapter<ThreadAdapter.VH>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_thread, parent, false)
            return VH(v, click)
        }

        override fun getItemCount() = 20

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.bind("Sender $position", position)
        }

        class VH(view: View, val click: ()->Unit): RecyclerView.ViewHolder(view) {
            val title = view.findViewById<TextView>(R.id.thread_title)
            val badge = view.findViewById<TextView>(R.id.thread_badge)
            init { view.setOnClickListener{ click() } }
            fun bind(name:String, pos:Int) {
                title.text = name
                badge.text = if (pos % 3 == 0) "NEW" else ""
            }
        }
    }
}
