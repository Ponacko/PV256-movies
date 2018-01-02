package cz.muni.fi.pv256.movio2.uco_433419

import android.app.Application
import android.os.StrictMode
import android.util.Log
import com.squareup.picasso.Picasso
import com.squareup.picasso.OkHttpDownloader



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
        val built = Picasso.Builder(this)
                .indicatorsEnabled(true)
                .loggingEnabled(true)
                .downloader(OkHttpDownloader(this, Integer.MAX_VALUE.toLong()))
                .build()
        Picasso.setSingletonInstance(built)
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
