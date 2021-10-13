package com.example.noted

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

private var TAG: String = "NotedApp"

class NoteListViewModel : ViewModel() {
    private val notedRepository = NotedRepository.get()
    val noteListLiveData = notedRepository.getNotes()

    private val folderLiveData = MutableLiveData<String>()
    var notesLiveData: LiveData<List<Note>> =
        Transformations.switchMap(folderLiveData) { folderTitle ->
            notedRepository.getNotesByFolder(folderTitle)
        }
    fun loadNotes(folderTitle: String) {
        folderLiveData.value = folderTitle
    }
    fun addNote(note: Note) {
        notedRepository.addNote(note)
    }
    fun deleteNote(note: Note){
        notedRepository.deleteNote(note)
    }


}