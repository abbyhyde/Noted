package com.example.noted.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.noted.Folder
import com.example.noted.Note

@Database(entities = [ Folder::class, Note::class ], version=1)
@TypeConverters(NotedTypeConverters::class)
abstract class NotedDatabase : RoomDatabase() {
    abstract fun notedDao(): NotedDao
}