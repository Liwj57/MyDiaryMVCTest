package com.example.mydiarymvctest

import android.app.Application

class EnApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        private var INSTANCE: EnApplication? = null
        fun get(): EnApplication {
            if (INSTANCE == null) {
                INSTANCE = EnApplication()
            }
            return INSTANCE!!
        }
    }
}