package com.example.noted

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import java.util.*

private const val ARG_NOTE_TITLE = "note_title"
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

    // companion object goes here
}