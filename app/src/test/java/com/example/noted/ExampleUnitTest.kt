package com.example.noted

import android.app.Application
import androidx.lifecycle.ViewModelProviders
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest{
//    lateinit var notedRepository: NotedRepository
//    lateinit var folderListViewModel : FolderListViewModel
//    lateinit var noteListViewModel: NoteListViewModel
//
//    override fun onCreate() {
//        NotedIntentApplication()
//        MainActivity()
//    }
//
//    @Before
//    fun setUp(){
//        NotedIntentApplication()
//        notedRepository = NotedRepository.get()
//        val folderListViewModel = FolderListViewModel()
//        val noteListViewModel = NoteListViewModel()
//    }
//
//    @Test
//    fun addFolder() {
//        var folders = folderListViewModel.folderListLiveData.value
//        var folderLength : Int
//        if (folders != null){
//            folderLength = folders.size
//        }else folderLength = 0
//        folderListViewModel.addFolder(Folder(UUID.randomUUID(), 0, "TestFolder", "white"))
//        var newFolderLength : Int? = folderListViewModel.folderListLiveData.value?.size
//
//        assertEquals((folderLength+1), newFolderLength)
//    }
//
//    @Test
//    fun deleteFolder(){
//        var folders = folderListViewModel.folderListLiveData.value
//        var folderLength : Int
//        if (folders != null){
//            folderLength = folders.size
//        }else folderLength = 0
//
//        folderListViewModel.addFolder(Folder(UUID.randomUUID(), 0, "TestFolder", "white"))
//        var folder2 : Folder = Folder(UUID.randomUUID(), 0, "TestFolder", "white")
//        folderListViewModel.addFolder(folder2)
//        folderListViewModel.deleteFolder(folder2)
//
//        var newFolderLength : Int? = folderListViewModel.folderListLiveData.value?.size
//
//
//        assertEquals((folderLength+1), newFolderLength)
//    }
//
//    @Test
//    fun editFolderTitle(){
//        var folder = Folder(UUID.randomUUID(), 0, "")
//    }
//
//    @Test
//    fun editFolderColor(){
//
//    }
//
//    @Test
//    fun addNote(){
//
//    }
//
//    @Test
//    fun deleteNote(){
//
//    }
//
//    @Test
//    fun updateNoteText(){
//
//    }
//
//    @Test
//    fun updateNoteTitle(){
//
//    }
}