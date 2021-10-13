package com.example.noted.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.noted.Folder
import com.example.noted.Note
import java.util.*

@Dao
interface NotedDao {

    @Query("SELECT * FROM folders")
    fun getFolders(): LiveData<List<Folder>>

    @Query("SELECT * FROM folders WHERE id=(:id)")
    fun getFolder(id: UUID): LiveData<Folder?>

    @Insert
    fun addFolder(folder: Folder)

    @Update
    fun updateFolder(folder: Folder)

    @Delete
    fun deleteFolder(folder: Folder)

    @Query("SELECT * FROM notes")
    fun getNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id=(:id)")
    fun getNote(id: UUID): LiveData<Note?>

    @Query("SELECT * FROM notes WHERE folderTitle=(:folderTitle)")
    fun getNotesByFolder(folderTitle: String): LiveData<List<Note>>

    @Insert
    fun addNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("DELETE FROM notes WHERE folderTitle=(:folderTitle)")
    fun deleteNotes(folderTitle: String)
}