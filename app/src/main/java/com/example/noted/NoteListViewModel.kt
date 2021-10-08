package com.example.noted

import androidx.lifecycle.ViewModel

private var TAG: String = "NotedApp"

class NoteListViewModel : ViewModel() {
    val notes = mutableListOf<Note>()
    init {
        for (i in 0 until 10) {
            val note = Note()
            note.title = "Note #$i"
            notes += note
        }
    }
}