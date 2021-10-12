package com.example.noted

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val ARG_FOLDER_TITLE = "folder_title"
private var TAG: String = "NotedApp"

class NoteListFragment : Fragment() {
    interface Callbacks{
        fun onNoteSelected(noteName: String, folderTitle: String)
        fun backToFolderList()
    }
    private var callbacks: Callbacks? = null
    private var adapter: NoteAdapter? = null
    private val noteListViewModel: NoteListViewModel by lazy {
        ViewModelProviders.of(this).get(NoteListViewModel::class.java)
    }
    private lateinit var noteListRecyclerView: RecyclerView
    private lateinit var noteListBackButton: ImageButton
    private lateinit var folderTitle: TextView

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "NoteListFragment onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "NoteListFragment onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "NLF onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "NLF onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "NFL onDestroy() called")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }
    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate of NoteListFragment, Total notes: ${noteListViewModel.notes.size}")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView of NLF")

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notes_list, container, false)
        noteListRecyclerView =
            view.findViewById(R.id.noteListRecyclerView) as RecyclerView
        noteListRecyclerView.layoutManager = LinearLayoutManager(context)
        noteListBackButton = view.findViewById(R.id.notesListBackButton) as ImageButton

        noteListBackButton.setOnClickListener{ view: View->
            // go back to folderList
            Log.d(TAG, "back button pressed")
            callbacks?.backToFolderList()
            Log.d(TAG, "backToFolderList() called")
        }

        val folderTitle: String = arguments?.getSerializable(ARG_FOLDER_TITLE) as String
        Log.d(TAG, "args bundle note title: $folderTitle")
        this.folderTitle = view.findViewById(R.id.folderNameLabel) as TextView
        this.folderTitle.text = folderTitle

        updateUI()
        return view
    }

    private fun updateUI() {
        val notes = noteListViewModel.notes
        adapter = NoteAdapter(notes, folderTitle.text.toString())
        noteListRecyclerView.adapter = adapter
    }

    private inner class NoteHolder(view: View, folderTitleParam: String)
        : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var note: Note
        private val titleTextView: TextView = itemView.findViewById(R.id.noteLabel)
        private val folderTitle: String = folderTitleParam
        private val menuButton: ImageButton = itemView.findViewById(R.id.noteMenu)
        init {
            itemView.setOnClickListener(this)
            menuButton.setOnClickListener() {
                Log.d(TAG, "menu button clicked for " + titleTextView.text.toString())
                val intent = Intent(getActivity(), NotePopup::class.java)
                startActivity(intent)
            }
        }
        fun bind(note: Note) {
            this.note = note
            titleTextView.text = this.note.title
        }
        override fun onClick(v: View?) {
            Log.d(TAG, note.title)
            callbacks?.onNoteSelected(note.title, folderTitle)

        }
    }

    private inner class NoteAdapter(var notes: List<Note>, folderTitleParam: String)
        : RecyclerView.Adapter<NoteHolder>() {
        private var folderTitle: String = folderTitleParam

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : NoteHolder {
            val view = layoutInflater.inflate(R.layout.fragment_note, parent, false)
            return NoteHolder(view, this.folderTitle)
        }
        override fun getItemCount() = notes.size

        override fun onBindViewHolder(holder: NoteHolder, position: Int) {
            val note = notes[position]
            holder.bind(note)
        }
    }

    companion object {
        fun newInstance(folderTitle: String): NoteListFragment {
            val args = Bundle().apply {
                putSerializable(ARG_FOLDER_TITLE, folderTitle)
            }
            return NoteListFragment().apply {
                arguments = args
            }
        }
    }
}