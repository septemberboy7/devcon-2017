package me.myatminsoe.devcon2017

import android.app.Application
import me.myatminsoe.devcon2017.mdetect.MDetect

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        MDetect.init(this)
    }
}