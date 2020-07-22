package com.anilshatharashi.currencyconverter

import android.app.Application
import com.anilshatharashi.currencyconverter.presentation.di.appModule
import com.anilshatharashi.currencyconverter.presentation.di.dataModule
import com.anilshatharashi.currencyconverter.presentation.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

open class CurrencyRatesConverter : Application()  {

    protected val appComponent: MutableList<Module> = mutableListOf(appModule, dataModule, domainModule)

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(appComponent)
        }
    }

}
