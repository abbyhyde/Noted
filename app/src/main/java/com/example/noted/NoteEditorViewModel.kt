package com.example.noted

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*
private var TAG: String = "NotedApp"


class NoteEditorViewModel() : ViewModel(){
    private val notedRepository = NotedRepository.get()
    private val noteIdLiveData = MutableLiveData<UUID>()

    var noteLiveData: LiveData<Note?> =
        Transformations.switchMap(noteIdLiveData) { noteId ->
            notedRepository.getNote(noteId)
        }
    fun loadNote(noteId: UUID) {
        noteIdLiveData.value = noteId
    }
    fun saveNote(note: Note) {
        notedRepository.updateNote(note)
    }
}