package com.example.noted

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

private const val ARG_NOTE_TITLE = "note_title"
private const val ARG_FOLDER_TITLE = "folder_title"
private var TAG: String = "NotedApp"

class NoteEditorFragment : Fragment() {
    interface Callbacks {
        fun backToNoteList(folderTitle: String)
    }
    private var callbacks: Callbacks? = null
    lateinit var editNoteTitle: EditText
    lateinit var noteBackButton: ImageButton

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }
    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Eventually, load note from file system
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_note_editor, container, false)

        val noteTitle: String = arguments?.getSerializable(ARG_NOTE_TITLE) as String
        val folderTitle: String = arguments?.getSerializable(ARG_FOLDER_TITLE) as String
        Log.d(TAG, "args bundle note title: $noteTitle")
        editNoteTitle = view.findViewById(R.id.editNoteTitle) as EditText
        editNoteTitle.setText(noteTitle)
        noteBackButton = view.findViewById(R.id.noteBackButton)

        noteBackButton.setOnClickListener{ view: View ->
            // go back to notes list
            // need folder title
            Log.d(TAG, "note back button pressed")
            callbacks?.backToNoteList(folderTitle)
            Log.d(TAG, "backToNoteList finished")

        }

        return view
    }

    private fun updateUI() {
    }

    companion object {
        fun newInstance(noteTitle: String, folderTitle: String): NoteEditorFragment {
            val args = Bundle().apply {
                putSerializable(ARG_NOTE_TITLE, noteTitle)
                putSerializable(ARG_FOLDER_TITLE, folderTitle)
            }
            return NoteEditorFragment().apply {
                arguments = args
            }
        }
    }
}