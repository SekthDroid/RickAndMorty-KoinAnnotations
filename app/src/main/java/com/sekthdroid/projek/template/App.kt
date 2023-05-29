package com.sekthdroid.projek.template

import android.app.Application
import com.sekthdroid.projek.template.di.Injector

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Injector.init(this)
    }
}