package com.example.noted

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val ARG_FOLDER_TITLE = "folder_title"
private var TAG: String = "NotedApp"

class NoteListFragment : Fragment() {
    interface Callbacks{
        fun onNoteSelected(id: UUID)
        fun backToFolderList()
    }
    private var callbacks: Callbacks? = null
    private var adapter: NoteAdapter? = NoteAdapter(emptyList(), "")
    private val noteListViewModel: NoteListViewModel by lazy {
        ViewModelProviders.of(this).get(NoteListViewModel::class.java)
    }
    private lateinit var noteListRecyclerView: RecyclerView
    private lateinit var noteListBackButton: ImageButton
    private lateinit var folderTitle: TextView
    private lateinit var addNoteButton: ImageButton

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }
    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes_list, container, false)
        noteListRecyclerView = view.findViewById(R.id.noteListRecyclerView) as RecyclerView
        noteListRecyclerView.layoutManager = LinearLayoutManager(context)
        noteListBackButton = view.findViewById(R.id.notesListBackButton) as ImageButton
        noteListRecyclerView.adapter = adapter
        noteListBackButton.setOnClickListener{ view: View->
            callbacks?.backToFolderList()
        }
        val folderTitle: String = arguments?.getSerializable(ARG_FOLDER_TITLE) as String
        this.folderTitle = view.findViewById(R.id.folderNameLabel) as TextView
        this.folderTitle.text = folderTitle
        addNoteButton = view.findViewById(R.id.addNoteButton) as ImageButton
        addNoteButton.setOnClickListener{ view: View ->
            noteListViewModel.addNote(Note(UUID.randomUUID(), "Note", folderTitle, "", ""))
        }
        noteListViewModel.loadNotes(folderTitle)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteListViewModel.notesLiveData.observe(
            viewLifecycleOwner,
            Observer { notes ->
                notes?.let {
                    Log.i(TAG, "Got notes ${notes.size}")
                    updateUI(notes)
                }
            })
    }


    private fun updateUI(notes: List<Note>) {
        adapter = NoteAdapter(notes, folderTitle.text.toString())
        noteListRecyclerView.adapter = adapter
    }

    private inner class NoteHolder(view: View, folderTitleParam: String)
        : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var note: Note
        private val titleTextView: TextView = itemView.findViewById(R.id.noteLabel)
        private val folderTitle: String = folderTitleParam
        private val deleteNoteButton: ImageButton = itemView.findViewById(R.id.deleteNoteButton)
        init {
            itemView.setOnClickListener(this)
        }
        fun bind(note: Note) {
            this.note = note
            titleTextView.text = this.note.title

            deleteNoteButton.setOnClickListener{
                noteListViewModel.deleteNote(note)
            }
        }

        override fun onClick(v: View?) {
            Log.d(TAG, note.id.toString())
            callbacks?.onNoteSelected(note.id)
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