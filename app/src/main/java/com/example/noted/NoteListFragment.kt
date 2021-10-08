package com.example.noted

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private var TAG: String = "NotedApp"

class NoteListFragment : Fragment() {
    interface Callbacks{
        fun onNoteSelected(noteName: String)
    }
    private var callbacks: Callbacks? = null
    private var adapter: NoteAdapter? = null
    private val noteListViewModel: NoteListViewModel by lazy {
        ViewModelProviders.of(this).get(NoteListViewModel::class.java)
    }
    private lateinit var noteListRecyclerView: RecyclerView

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

        updateUI()
        return view
    }

    private fun updateUI() {
        val notes = noteListViewModel.notes
        adapter = NoteAdapter(notes)
        noteListRecyclerView.adapter = adapter
    }

    private inner class NoteHolder(view: View)
        : RecyclerView.ViewHolder(view) {
        val noteTextView: TextView = itemView.findViewById(R.id.noteLabel)
    }

    private inner class NoteAdapter(var notes: List<Note>)
        : RecyclerView.Adapter<NoteHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : NoteHolder {
            Log.d(TAG, "onCreateViewHolder called")
            val view = layoutInflater.inflate(R.layout.fragment_note, parent, false)
            Log.d(TAG, "did the thing")
            return NoteHolder(view)
        }
        override fun getItemCount() = notes.size

        override fun onBindViewHolder(holder: NoteHolder, position: Int) {
            Log.d(TAG, "onBindViewHolder called")
            val note = notes[position]
            holder.apply {
                noteTextView.text = note.title
            }
            Log.d(TAG, "end onBindViewHolder")
        }
    }

    companion object {
        fun newInstance(): NoteListFragment {
            return NoteListFragment()
        }
    }
}