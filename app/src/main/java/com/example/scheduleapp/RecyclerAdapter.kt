package com.example.scheduleapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.*

const val EXTRA_MESSAGE = "com.example.scheduleapp.MESSAGE"


class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var titles = arrayListOf("Run", "Walk", "Gym", "Drink Water", "Jumping", "Swimming", "Eating", "Talking")
    private var descriptions = arrayListOf("7pm", "Description?", "Time", "Upcoming time", "Can be any content", "Desc1", "Desc2", "Desc3")

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
        private var rc: Int = 0

        init {
            itemView.setOnClickListener {
                val position = absoluteAdapterPosition
                val duration = Toast.LENGTH_SHORT
                val text = "testing ${titles[position]}"
                val toast = Toast.makeText(itemView.context, text, duration)
                toast.show()

                val activity = itemView.context
                val intent = Intent(itemView.context, EditTaskActivity::class.java).apply{
                    putExtra(EXTRA_MESSAGE, "AAAAAAAAAAAaaaaaa")
//                    putExtra(EXTRA_MESSAGE, "testing ${titles[position]}")
                }
//                intent.putExtra(EXTRA_MESSAGE, "testing ${titles[position]}")
                activity.startActivity(intent)

                setAlarm(10000, "This is a test notification")
            }
        }

        // Set alarm to specify when notification is triggered
        private fun setAlarm(timeBeforeTrigger: Long, notificationMessage : String) {
            // Create AlarmManager to help set alarm
            val alarmManager = itemView.context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            // Create Intent which will trigger notification to be sent
            val intent = Intent(itemView.context, AlarmReceiver::class.java)
            intent.putExtra("notificationMessage", notificationMessage)

            lateinit var pendingIntent: PendingIntent
            if (Build.VERSION.SDK_INT >= 23) {
                pendingIntent = PendingIntent.getBroadcast(itemView.context, this.rc, intent, PendingIntent.FLAG_IMMUTABLE)        // when Version >= 23, need to include mutability flag
            } else {
                pendingIntent = PendingIntent.getBroadcast(itemView.context, this.rc, intent, 0)
            }

            // Set when the alarm is triggered
            val calendar = Calendar.getInstance()
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis + timeBeforeTrigger, pendingIntent)

            incrementRc()
        }

        // Increments rc
        private fun incrementRc() {
            rc++
        }
    }
}