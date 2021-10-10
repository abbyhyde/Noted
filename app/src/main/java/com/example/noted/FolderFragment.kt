package com.example.noted

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import java.util.*

private var TAG: String = "NotedApp"

class FolderFragment : Fragment() {
    private lateinit var FolderLabel: TextView
    private lateinit var FolderMenu: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "inside onCreate of FolderFragment")
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_folder, container, false)
        FolderLabel = view.findViewById(R.id.folderLabel)
        FolderMenu = view.findViewById(R.id.folderMenu)

        FolderMenu.setOnClickListener{ view: View ->
            // pop up menu
        }

        return view
    }

    // check this compared to NoteFragment and NoteEditorFragment about where things should go
    companion object {
        fun newInstance(): FolderFragment {
            return FolderFragment()
        }
    }
}