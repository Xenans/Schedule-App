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
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import java.io.BufferedReader




const val EXTRA_MESSAGE = "com.example.scheduleapp.MESSAGE"


class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var test = ActivityClass("Run", "7pm", false)
    private var test2 = ActivityClass("Walk", "Description2", false)
//    private var activityClasses = arrayListOf(test, test2)
    private var activityClasses = arrayListOf(test)


    private var titles = arrayListOf("Run", "Walk", "Gym", "Drink Water", "Jumping", "Swimming", "Eating", "Talking")
    private var descriptions = arrayListOf("7pm", "Description?", "Time", "Upcoming time", "Can be any content", "Desc1", "Desc2", "Desc3")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)

        // Wipe file as necessary
        val file = File(view.context.filesDir, "PostJson.json")
        file.writeText("[{\"description\":\"7pm\",\"isRecurring\":false,\"title\":\"Run\"},{\"description\":\"wadfwadf\",\"isRecurring\":false,\"title\":\"Run\"}]")

        val text = getData(view.context)
        if (text != null) {
            Log.v("WRITINGWRIETNERWIG", text.toString())
            activityClasses.clear()
            activityClasses.addAll(text)
//            this.notifyDataSetChanged()
        }
        Log.v("LWEJFLWEKFJWELKFJWREAD", activityClasses.toString())
        text?.forEach { Log.v("FDAFSDAFDSA", it.toString()) }
        activityClasses?.forEach { Log.v("RQWEQWEWQEQW", it.toString()) }



//        var testTask = gson.fromJson(text, ActivityClass::class.java)
//        Log.v("GFDSAGFDSGF!!!!!!!!!", text, )


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

        Log.v("IMWRITINGDOWN", activity.toString())
        val file = File(context.filesDir, "PostJson.json")
        val jsonString: String = Gson().toJson(activity)
        file.writeText(jsonString, Charsets.UTF_8)
        Log.v("DSADSADSADSADSASAVED", jsonString)
    }

    private fun getData(context: Context): Array<ActivityClass>? {
        val file = File(context.filesDir, "PostJson.json")
        val bufferedReader: BufferedReader = file.bufferedReader()
        val inputString = bufferedReader.use { it.readText() }
        var gson = GsonBuilder().setLenient().create()

        return gson.fromJson(inputString, Array<ActivityClass>::class.java)
    }
}