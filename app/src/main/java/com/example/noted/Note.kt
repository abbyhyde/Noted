package com.example.noted

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName="notes")
class Note (@PrimaryKey val id: UUID,
     var title: String,
     var folderTitle: String,
     var location: String,
     var body: String,
){}