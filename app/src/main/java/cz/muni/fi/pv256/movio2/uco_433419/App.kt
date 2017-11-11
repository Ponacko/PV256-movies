package cz.muni.fi.pv256.movio2.uco_433419

import android.app.Application
import android.os.StrictMode
import android.util.Log

/**
 * Created by Tomas on 29. 9. 2017.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            activateStrictMode()
        }
        if (BuildConfig.LOGGING) {
            Log.d("Logging works", "App")
        }
    }

    private fun activateStrictMode() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build())
    }
}
