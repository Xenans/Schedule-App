package com.example.scheduleapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import android.widget.CompoundButton




const val EXTRA_MESSAGE = "com.example.scheduleapp.MESSAGE"


class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var titles = arrayListOf("Run", "Walk", "Gym", "Drink Water", "Jumping", "Swimming", "Eating", "Talking", "Run", "Walk", "Gym", "Drink Water", "Jumping", "Swimming", "Eating", "Talking")
    private var descriptions = arrayListOf("7pm", "Description?", "Time", "Upcoming time", "Can be any content", "Desc1", "Desc2", "Desc3", "7pm", "Description?", "Time", "Upcoming time", "Can be any content", "Desc1", "Desc2", "Desc3")
    private var isDone = mutableListOf(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    // iterate for cards?
    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        if (isDone[position]) {
            holder.itemTitle.setPaintFlags(holder.itemTitle.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        } else {
            holder.itemTitle.setPaintFlags(holder.itemTitle.getPaintFlags() and Paint.STRIKE_THRU_TEXT_FLAG.inv())
        }

        holder.itemDetail.text = descriptions[position]

        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = isDone[position]
        holder.checkBox.setOnCheckedChangeListener {_: CompoundButton, isChecked: Boolean ->
            isDone[position] = isChecked
            holder.checkBox.isChecked = isDone[position]
            if (isDone[position]) {
                holder.itemTitle.setPaintFlags(holder.itemTitle.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
            } else {
                holder.itemTitle.setPaintFlags(holder.itemTitle.getPaintFlags() and Paint.STRIKE_THRU_TEXT_FLAG.inv())
            }
        }
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    // Creates each task card
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView = itemView.findViewById(R.id.item_title)
        var itemDetail: TextView = itemView.findViewById(R.id.item_detail)
        var checkBox: CheckBox = itemView.findViewById(R.id.item_checkBox)

        var taskId: Int = 0                                                         // simulate task id, should actually be stored along with the task
        var timeBeforeTrigger: Long = 10000                                         // simulate time before trigger, should actually be parsed from what the user sets in the task
        var notificationMessage: String = "This is a test notification"             // simulate notification message, should actually be parsed from task

        // Initialize listeners
        init {
            // Listener for when the card is clicked
            itemView.setOnClickListener {
                val position = absoluteAdapterPosition
//                val duration = Toast.LENGTH_SHORT
//                val text = "testing ${titles[position]}"
//                val toast = Toast.makeText(itemView.context, text, duration)
//                toast.show()

                val activity = itemView.context
                val intent = Intent(itemView.context, EditTaskActivity::class.java).apply{
                    putExtra(EXTRA_MESSAGE, "testing ${titles[position]}")
//                    putExtra(EXTRA_MESSAGE, "testing ${titles[position]}")
                }
                activity.startActivity(intent)

                setAlarm(timeBeforeTrigger, notificationMessage, taskId)
            }

            // Listener for when the checkbox that is part of the card is clicked
            itemView.findViewById<CheckBox>(R.id.item_checkBox).setOnClickListener {
                // Remove notification for the task (recreate the PendingIntent to remove it)
                cancelAlarm(notificationMessage, taskId)
            }

            incrementTaskId()
        }

        // Set alarm to specify when notification is triggered
        private fun setAlarm(timeBeforeTrigger: Long, notificationMessage: String, taskId: Int) {
            // Create AlarmManager to help set alarm
            val alarmManager = itemView.context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            // Create Intent which will trigger notification to be sent
            val intent = Intent(itemView.context, AlarmReceiver::class.java)
            intent.putExtra("notificationMessage", notificationMessage)

            lateinit var pendingIntent: PendingIntent
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                pendingIntent = PendingIntent.getBroadcast(itemView.context, taskId, intent, PendingIntent.FLAG_MUTABLE)        // when Version >= 23, need to include mutability flag
            } else {
                pendingIntent = PendingIntent.getBroadcast(itemView.context, taskId, intent, 0)
            }

            // Set when the alarm is triggered
            val calendar = Calendar.getInstance()
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis + timeBeforeTrigger, pendingIntent)
        }

        // Cancel alarm for a specific notification
        private fun cancelAlarm(notificationMessage: String, taskId: Int) {
            // Create AlarmManager to help set alarm
            val alarmManager = itemView.context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            // Create Intent which will trigger notification to be sent
            val intent = Intent(itemView.context, AlarmReceiver::class.java)
            intent.putExtra("notificationMessage", notificationMessage)

            lateinit var pendingIntent: PendingIntent
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                pendingIntent = PendingIntent.getBroadcast(itemView.context, taskId, intent, PendingIntent.FLAG_MUTABLE)        // when Version >= 23, need to include mutability flag
            } else {
                pendingIntent = PendingIntent.getBroadcast(itemView.context, taskId, intent, 0)
            }

            alarmManager.cancel(pendingIntent)
        }

        // Increments task id
        private fun incrementTaskId() {
            taskId++
        }
    }
}