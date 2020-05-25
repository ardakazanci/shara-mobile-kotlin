package co.shara

import com.facebook.stetho.Stetho

class SharaDebug : Shara() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

}