package com.example.noted

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

private var TAG: String = "NotedApp"

class NoteFragment : Fragment() {
    private lateinit var NoteLabel: TextView
    private lateinit var NoteMenu: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "inside onCreate of NoteFragment")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_note, container, false)

        NoteLabel = view.findViewById(R.id.noteLabel);
        NoteMenu = view.findViewById(R.id.noteMenu);

        NoteMenu.setOnClickListener{ view: View ->
            // pop up menu
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment NoteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NoteFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}