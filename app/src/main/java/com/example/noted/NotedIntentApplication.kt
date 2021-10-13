package com.example.noted

import android.app.Application

class NotedIntentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NotedRepository.initialize(this)
    }
}