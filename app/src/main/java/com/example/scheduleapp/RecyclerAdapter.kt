package com.example.scheduleapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

const val EXTRA_MESSAGE = "com.example.blank.MESSAGE"


class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var titles = arrayOf("Run", "Walk", "Gym", "Drink Water", "Jumping", "Swimming", "Eating", "Talking")
    private var descriptions = arrayOf("7pm", "Description?", "Time", "Upcoming time", "Can be any content", "Desc1", "Desc2", "Desc3")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    // iterate for cards?
    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemDetail.text = descriptions[position]
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView = itemView.findViewById(R.id.item_title)
        var itemDetail: TextView = itemView.findViewById(R.id.item_detail)

        init {
            itemView.setOnClickListener {
                val position = absoluteAdapterPosition
                val duration = Toast.LENGTH_SHORT
//                val text = "testing ddddd"
                val text = "testing ${titles[position]}"

                val toast = Toast.makeText(itemView.context, text, duration)
                toast.show()


//                val editText = findViewById<EditText>(R.id.editTextTextPersonName)
//                val message = editText.text.toString()
//                val intent = Intent(this, DisplayMessageActivity::class.java).apply {
//                    putExtra(EXTRA_MESSAGE, message)
//                }
//                startActivity(intent)


            }
        }
    }
}