package com.example.noted

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

private var TAG: String = "NotedApp"

class FolderListViewModel : ViewModel() {
    private val notedRepository = NotedRepository.get()
    val folderListLiveData = notedRepository.getFolders()

    private val folderIdLiveData = MutableLiveData<UUID>()

    var folderLiveData: LiveData<Folder?> =
        Transformations.switchMap(folderIdLiveData) { folderId ->
            notedRepository.getFolder(folderId)
        }

    fun saveFolder(folder: Folder) {
        notedRepository.updateFolder(folder)
    }
    fun addFolder(folder: Folder) {
        notedRepository.addFolder(folder)
    }
    fun deleteFolder(folder: Folder){
        notedRepository.deleteFolder(folder)
    }
    fun loadFolder(folderId: UUID){
        folderIdLiveData.value = folderId
    }
}