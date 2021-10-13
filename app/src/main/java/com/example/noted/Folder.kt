package com.example.noted

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName="folders")
data class Folder (
    @PrimaryKey var id: UUID = UUID.randomUUID(),
    var title: String = "Folder",
    var color: String = "white"
){
}