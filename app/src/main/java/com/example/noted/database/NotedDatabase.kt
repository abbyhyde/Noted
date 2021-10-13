package com.example.noted.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.noted.Folder
import com.example.noted.Note

@Database(entities = [ Folder::class, Note::class ], version=2)
@TypeConverters(NotedTypeConverters::class)
abstract class NotedDatabase : RoomDatabase() {
    abstract fun notedDao(): NotedDao
}

val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE folders2 (id TEXT PRIMARY KEY NOT NULL, title TEXT NOT NULL, color TEXT NOT NULL)")
        database.execSQL(
            "INSERT INTO folders2(id, title, color) SELECT id, title, color FROM folders")
        database.execSQL(
            "DROP TABLE folders")
        database.execSQL(
            "ALTER TABLE folders2 RENAME TO folders")
    }
}
