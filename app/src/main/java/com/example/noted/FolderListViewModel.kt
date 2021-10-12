package com.example.noted

import androidx.lifecycle.ViewModel

private var TAG: String = "NotedApp"

class FolderListViewModel : ViewModel() {
    val folders = mutableListOf<Folder>()
    init {
        for (i in 0 until 10) {
            val folder = Folder()
            folder.title = "Folder #$i"
            folder.color = "purple"
            folders += folder
        }
    }
}