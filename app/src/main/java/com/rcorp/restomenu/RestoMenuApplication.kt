package com.rcorp.restomenu

import android.app.Application
import android.util.Log
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent

class RestoMenuApplication : Application() {
    /**
     * Initialise all application level lib and module
     */
    override fun onCreate() {
        super.onCreate()
        // Start dependencies injection
        startKoin {
            androidLogger()
            androidContext(this@RestoMenuApplication)
            modules(listOf(appModule))
        }
        RxJavaPlugins.setErrorHandler { throwable ->
            Log.e(
                TAG,
                "WARNING !!!! Unhandle exception",
                throwable
            )
        }

        val picasso = Picasso.Builder(this)
            .downloader(OkHttp3Downloader(this, Long.MAX_VALUE))
            .build()

        Picasso.setSingletonInstance(picasso)

        Picasso.get().isLoggingEnabled = BuildConfig.DEBUG
        Picasso.get().setIndicatorsEnabled(BuildConfig.DEBUG)
    }

    /**
     * Initialisation of all application global object.
     * All of there object can be inject anywhere in the application
     */
    companion object {


        val appModule = module {}
        private val TAG = this::class.simpleName
    }
}