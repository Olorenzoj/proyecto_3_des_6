package com.example.project3

import android.app.Application
import com.example.project3.database.DatabaseHelper

class App : Application() {
    companion object {
        lateinit var databaseHelper: DatabaseHelper
    }

    override fun onCreate() {
        super.onCreate()
        databaseHelper = DatabaseHelper(this)
    }
}