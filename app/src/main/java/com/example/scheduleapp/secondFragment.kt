package com.example.scheduleapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [secondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class secondFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var sugGenerator: SuggestionGenerator = SuggestionGenerator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.suggestionRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(view.context)

        recyclerView.adapter = RecyclerAdapter()
        val textView = view.findViewById<TextView>(R.id.textView2).apply {
            text = sugGenerator.getSuggestion()
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment secondFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            secondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

class SuggestionGenerator {
    var array = arrayOf("Say hi to someone new",
    "Ask an old friend how their day is going",
    "Perform stretches, yoga, or other light exercise",
    "Consider going to a Beyonce concert this Friday",
    "Try a new food today",
    "Hail hydrate",
    "Perform 20 push-ups",
    "Perform 20 sit-ups",
    "Try a new restaurant near your house",
    "Try cooking a new recipe",
    "Go for a walk at your nearest part",
    "Visit a library and read a book or two",
    "Spend the day at a friend's place",
    "Go swimming!",
    "Try dragon boating",
    "Lift some weights",
    "Go hiking today",
    "Try your hand at programming",
    "Learn a new language",
    "Take up drawing",
    "Change your look, try something new!",
    "Try a new drink",
    "Try sewing",
    "try gardening",
    "Listen to a new genre of music",
    "Visit a new park",
    "Visit a new town",
    "Go on a scenic walk or bicycle ride",
    "Renovate your house",
    "Clean your room",
    "Learn a new instrument",
    "Go camping",
    "Go picnicking",
    "Visit a museum",
    "Go on a road trip",
    "Splurge on that purchase!",
    "Host a dinner for friends",
    "Host a party",
    "Meditate",
    "Do some volunteer work",
    "Donate to a charity",
    "Dance",
    "Sing",
    "Try some new puzzles",
    "Watch a new TV series",
    "Watch a movie",
    "Read a book")


    fun getSuggestion() : String {
        return array[Random.nextInt(array.size)]
    }

}