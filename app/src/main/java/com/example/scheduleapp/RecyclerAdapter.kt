package com.example.scheduleapp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import com.google.gson.Gson
import java.io.BufferedReader

const val EXTRA_MESSAGE = "com.example.scheduleapp.MESSAGE"


class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var test = ActivityClass("Run", "7pm", false)
    private var test2 = ActivityClass("Walk", "Description?", false)
    private var activityClasses = arrayListOf(test, test2)

    private var titles = arrayListOf("Run", "Walk", "Gym", "Drink Water", "Jumping", "Swimming", "Eating", "Talking")
    private var descriptions = arrayListOf("7pm", "Description?", "Time", "Upcoming time", "Can be any content", "Desc1", "Desc2", "Desc3")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        val text = getData(view.context)
        Log.v("LWEJFLWEKFJWELKFJWELKFJ", text, )
        return ViewHolder(view)
    }

    // iterate for cards?
    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = activityClasses[position].title
        holder.itemDetail.text = activityClasses[position].description
    }

    override fun getItemCount(): Int {
        return activityClasses.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView = itemView.findViewById(R.id.item_title)
        var itemDetail: TextView = itemView.findViewById(R.id.item_detail)

        init {
            itemView.setOnClickListener {
                val position = absoluteAdapterPosition
                val duration = Toast.LENGTH_SHORT
                val text = "testing ${activityClasses[position].title}"

                val toast = Toast.makeText(itemView.context, text, duration)
                toast.show()

//                // Test stuff
                saveData(activityClasses, itemView.context)

                val activity = itemView.context
                val intent = Intent(itemView.context, EditTaskActivity::class.java).apply{
                    putExtra(EXTRA_MESSAGE, "AAAAAAAAAAAaaaaaa")
//                    putExtra(EXTRA_MESSAGE, "testing ${titles[position]}")
                }
//                intent.putExtra(EXTRA_MESSAGE, "testing ${titles[position]}")
                activity.startActivity(intent)
            }
        }
    }

    fun addActivity(title: String, description: String, isRecurring: Boolean) {
        val activityClass = ActivityClass(title, description, isRecurring)
        activityClasses.add(activityClass)
        val index = activityClasses.size + 1
        this.notifyItemInserted(index)
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