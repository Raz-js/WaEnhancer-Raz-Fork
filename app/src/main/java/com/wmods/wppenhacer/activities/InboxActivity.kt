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
import com.wmods.wppenhacer.gmail.GmailClient
import com.wmods.wppenhacer.gmail.ThreadItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InboxActivity : AppCompatActivity() {
    private lateinit var adapter: ThreadAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inbox)
        val rv = findViewById<RecyclerView>(R.id.recycler_threads)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = ThreadAdapter {
            startActivity(Intent(this, ConversationActivity::class.java))
        }
        rv.adapter = adapter

        // load token from preferences
        val token = getSharedPreferences("auth", MODE_PRIVATE).getString("gmail_token", null)
        if (!token.isNullOrEmpty()) {
            val client = GmailClient.create(token)
            client.listThreads().enqueue(object: Callback<com.wmods.wppenhacer.gmail.ThreadsListResponse> {
                override fun onResponse(call: Call<com.wmods.wppenhacer.gmail.ThreadsListResponse>, response: Response<com.wmods.wppenhacer.gmail.ThreadsListResponse>) {
                    val threads = response.body()?.threads ?: emptyList()
                    runOnUiThread { adapter.setThreads(threads) }
                }
                override fun onFailure(call: Call<com.wmods.wppenhacer.gmail.ThreadsListResponse>, t: Throwable) {}
            })
        }
    }

    class ThreadAdapter(val click: ()->Unit): RecyclerView.Adapter<ThreadAdapter.VH>() {
        private var threads: List<ThreadItem> = emptyList()
        fun setThreads(list: List<ThreadItem>) { threads = list; notifyDataSetChanged() }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_thread, parent, false)
            return VH(v, click)
        }

        override fun getItemCount() = threads.size

        override fun onBindViewHolder(holder: VH, position: Int) {
            val t = threads[position]
            holder.bind(t)
        }

        class VH(view: View, val click: ()->Unit): RecyclerView.ViewHolder(view) {
            val title = view.findViewById<TextView>(R.id.thread_title)
            val badge = view.findViewById<TextView>(R.id.thread_badge)
            init { view.setOnClickListener{ click() } }
            fun bind(item: ThreadItem) {
                title.text = item.snippet ?: item.id
                badge.text = "" // unread info will be added later
            }
        }
    }
}
