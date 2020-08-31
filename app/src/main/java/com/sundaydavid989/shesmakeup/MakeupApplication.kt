package com.sundaydavid989.shesmakeup

import android.app.Application
import com.sundaydavid989.shesmakeup.data.db.MakeupDatabase
import com.sundaydavid989.shesmakeup.data.network.*
import com.sundaydavid989.shesmakeup.data.repository.MakeupRepository
import com.sundaydavid989.shesmakeup.data.repository.MakeupRepositoryImpl
import com.sundaydavid989.shesmakeup.ui.home.HomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MakeupApplication : Application(), KodeinAware {
    override val kodein= Kodein.lazy {
        import(androidXModule(this@MakeupApplication))

        bind() from singleton { MakeupDatabase(instance()) }
        bind() from singleton { instance<MakeupDatabase>().makeupItemDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { MakeupApiService(instance()) }
        bind<MakeupNetworkDataSource>() with singleton { MakeupNetworkDataSourceImpl(instance()) }
        bind<MakeupRepository>() with singleton { MakeupRepositoryImpl(instance(), instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
    }
}