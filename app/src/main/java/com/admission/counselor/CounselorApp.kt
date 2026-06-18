package com.admission.counselor

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CounselorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialization logic for App-wide singletons
    }
}
