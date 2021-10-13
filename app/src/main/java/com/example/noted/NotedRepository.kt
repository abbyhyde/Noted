package com.example.noted

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.noted.database.NotedDatabase
import java.util.*
import java.util.concurrent.Executors
private var TAG: String = "NotedApp"

private const val DATABASE_NAME = "NotedDB"

class NotedRepository private constructor(context: Context){
    private val database : NotedDatabase = Room.databaseBuilder(
        context.applicationContext,
        NotedDatabase::class.java,
        DATABASE_NAME
    ).build()
    private val notedDao = database.notedDao()
    private val executor = Executors.newSingleThreadExecutor()


    fun getFolders(): LiveData<List<Folder>> = notedDao.getFolders()
    fun getFolder(id: UUID): LiveData<Folder?> = notedDao.getFolder(id)
    fun updateFolder(folder: Folder){
        executor.execute{
            notedDao.updateFolder(folder)
        }
    }
    fun addFolder(folder: Folder){
        executor.execute{
            notedDao.addFolder(folder)
        }
    }
    fun deleteFolder(folder: Folder){
        executor.execute{
            notedDao.deleteFolder(folder)
            if (notedDao.getNotesByFolder(folder.title)!=null){
                notedDao.deleteNotes(folder.title)
            }
        }
    }

    fun getNotes(): LiveData<List<Note>> = notedDao.getNotes()
    fun getNotesByFolder(folderTitle: String): LiveData<List<Note>> = notedDao.getNotesByFolder(folderTitle)
    fun getNote(id: UUID): LiveData<Note?> = notedDao.getNote(id)
    fun updateNote(note: Note){
        executor.execute{
            notedDao.updateNote(note)
        }
    }
    fun addNote(note: Note){
        executor.execute{
            notedDao.addNote(note)
        }
    }
    fun deleteNote(note: Note){
        executor.execute{
            notedDao.deleteNote(note)
        }
    }

    companion object {
        private var INSTANCE: NotedRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = NotedRepository(context)
            }
        }
        fun get(): NotedRepository {
            return INSTANCE ?:
            throw IllegalStateException("NotedRepository must be initialized")
        }
    }
}