package com.example.scheduleapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import android.widget.CompoundButton
import java.io.File
import com.google.gson.Gson
import java.io.BufferedReader

const val EXTRA_MESSAGE = "com.example.scheduleapp.MESSAGE"


class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var test = ActivityClass("Run", "7pm", false)
    private var test2 = ActivityClass("Walk", "Description?", false)
    private var activityClasses = arrayListOf(test, test2)

    private var titles = arrayListOf("Run", "Walk", "Gym", "Drink Water", "Jumping", "Swimming", "Eating", "Talking")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        val text = getData(view.context)
        Log.v("LWEJFLWEKFJWELKFJWELKFJ", text, )
        return ViewHolder(view)
    }

    // iterate for cards?
    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = activityClasses[position].title
        if (activityClasses[position].isRecurring) {
            holder.itemTitle.paintFlags = holder.itemTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.itemTitle.paintFlags = holder.itemTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        holder.itemDetail.text = activityClasses[position].description

        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = activityClasses[position].isRecurring
        holder.checkBox.setOnCheckedChangeListener {_: CompoundButton, isChecked: Boolean ->
            activityClasses[position].isRecurring = isChecked
            holder.checkBox.isChecked = activityClasses[position].isRecurring
            if (activityClasses[position].isRecurring) {
                holder.itemTitle.paintFlags = holder.itemTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                holder.itemTitle.paintFlags = holder.itemTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

    }

    override fun getItemCount(): Int {
        return activityClasses.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView = itemView.findViewById(R.id.item_title)
        var itemDetail: TextView = itemView.findViewById(R.id.item_detail)
        var checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        private var rc: Int = 0

        init {
            itemView.setOnClickListener {
                val position = absoluteAdapterPosition

//                // Test stuff
                saveData(activityClasses, itemView.context)

                val activity = itemView.context
                val intent = Intent(itemView.context, EditTaskActivity::class.java).apply {
                    putExtra(EXTRA_MESSAGE, "testing ${activityClasses[position].title}")
                }
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

    fun addActivity(context: Context, title: String, description: String, isRecurring: Boolean) {
        val activityClass = ActivityClass(title, description, isRecurring)
        activityClasses.add(activityClass)
        val index = activityClasses.size + 1
        this.notifyItemInserted(index)
        saveData(activityClasses, context)
    }

    fun removeActivity(context: Context, index : Int) {
        activityClasses.removeAt(index)
        this.notifyItemRemoved(index)
        saveData(activityClasses, context)
    }

    private fun saveData(activity: ArrayList<ActivityClass>, context: Context) {

        val file = File(context.filesDir, "PostJson.json")
        file.writeText("", Charsets.UTF_8)
        for (item in activity) {
            val jsonString: String = Gson().toJson(item)
            file.appendText(jsonString, Charsets.UTF_8)
        }
    }

    private fun getData(context: Context): String {
        //activityClasses = arrayListOf()
        val file = File(context.filesDir, "PostJson.json")
        val bufferedReader: BufferedReader = file.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        //activityClasses += the item
        // temporarily returning this
        return inputString
    }
}