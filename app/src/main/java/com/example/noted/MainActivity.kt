package com.example.noted

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

private var TAG: String = "NotedApp"

class MainActivity : AppCompatActivity(), FolderListFragment.Callbacks,
    NoteListFragment.Callbacks, NoteEditorFragment.Callbacks {

        override fun onCreate(savedInstanceState: Bundle?) {
            Log.d(TAG, "inside OnCreate of main")
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

            if (currentFragment == null){
                val fragment = FolderListFragment.newInstance()
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
            }

        }

        override fun onFolderSelected(folderTitle: String) {
            val fragment = NoteListFragment.newInstance(folderTitle)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        override fun onNoteSelected(noteTitle: String, folderTitle: String) {
            val fragment = NoteEditorFragment.newInstance(noteTitle, folderTitle)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        override fun backToNoteList(folderTitle: String) {
            val fragment = NoteListFragment.newInstance(folderTitle)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }

        override fun backToFolderList() {
            val fragment = FolderListFragment.newInstance()

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }

}