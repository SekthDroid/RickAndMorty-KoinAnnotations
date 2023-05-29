package com.sekthdroid.projek.template.initializer

import android.content.Context
import androidx.startup.Initializer
import com.sekthdroid.projek.template.data.DataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule
import org.koin.ksp.generated.module

class KoinInitializer : Initializer<Koin> {
    override fun create(context: Context): Koin {
        return startKoin {
            androidContext(context)
            modules(DataModule().module, defaultModule)
        }.koin
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

}