package com.example.noted

import androidx.lifecycle.ViewModel

class FolderListViewModel : ViewModel() {
    val folders = mutableListOf<Folder>()
    init {

    }
}