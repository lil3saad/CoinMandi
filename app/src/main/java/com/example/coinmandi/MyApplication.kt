package com.example.coinmandi

import android.app.Application
import com.example.coinmandi.dependencyinjection.coindetailModule
import com.example.coinmandi.dependencyinjection.auth_featureModule
import com.example.coinmandi.dependencyinjection.coreModule
import com.example.coinmandi.dependencyinjection.exploreModule
import com.example.coinmandi.dependencyinjection.homefeatureModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(auth_featureModule , homefeatureModule , coreModule , exploreModule ,
                coindetailModule
            )
            androidLogger()
        }
    }
}