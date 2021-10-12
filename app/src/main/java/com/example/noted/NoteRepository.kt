package com.example.noted

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import androidx.core.app.ActivityCompat.startActivityForResult
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.Executors.newSingleThreadExecutor

const val CREATE_FILE = 1
const val PICK_PDF_FILE = 2


class NoteRepository private constructor(context: Context){
    private val executor = Executors.newSingleThreadExecutor()
    val filesDir = context.applicationContext.filesDir
}