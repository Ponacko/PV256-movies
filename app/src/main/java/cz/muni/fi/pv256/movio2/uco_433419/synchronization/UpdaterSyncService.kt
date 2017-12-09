package cz.muni.fi.pv256.movio2.uco_433419.synchronization

import android.app.Service
import android.content.Intent
import android.os.IBinder


/**
 * @author Tomáš Stolárik <tomas.stolarik@dactylgroup.com>
 */
class UpdaterSyncService : Service() {

    override fun onCreate() {
        synchronized(LOCK) {
            if (sUpdaterSyncAdapter == null) {
                sUpdaterSyncAdapter = UpdaterSyncAdapter(applicationContext, true)
            }
        }
    }

    override fun onBind(intent: Intent): IBinder = sUpdaterSyncAdapter!!.syncAdapterBinder

    companion object {

        private val LOCK = Any()
        private var sUpdaterSyncAdapter: UpdaterSyncAdapter? = null
    }
}