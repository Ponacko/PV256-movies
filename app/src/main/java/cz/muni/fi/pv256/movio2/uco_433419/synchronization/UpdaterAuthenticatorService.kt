package cz.muni.fi.pv256.movio2.uco_433419.synchronization

import android.app.Service
import android.content.Intent
import android.os.IBinder


/**
 * @author Tomáš Stolárik <tomas.stolarik@dactylgroup.com>
 */
/**
 * The service which allows the sync adapter framework to access the authenticator.
 */
class UpdaterAuthenticatorService : Service() {
    // Instance field that stores the authenticator object
    private var mAuthenticator: UpdaterAuthenticator? = null

    override fun onCreate() {
        // Create a new authenticator object
        mAuthenticator = UpdaterAuthenticator(this)
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    override fun onBind(intent: Intent): IBinder = mAuthenticator!!.iBinder
}