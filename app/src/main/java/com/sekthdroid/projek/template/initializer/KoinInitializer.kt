package com.sekthdroid.projek.template.initializer

import android.content.Context
import androidx.startup.Initializer
import com.sekthdroid.projek.template.data.dataModule
import com.sekthdroid.projek.template.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.context.startKoin

class KoinInitializer : Initializer<Koin> {
    override fun create(context: Context): Koin {
        return startKoin {
            androidContext(context)
            modules(dataModule, uiModule)
        }.koin
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

}