package cz.muni.fi.pv256.movio2.uco_433419.synchronization

import android.accounts.Account
import android.accounts.AccountManager
import android.content.*
import android.os.Bundle
import cz.muni.fi.pv256.movio2.uco_433419.R
import cz.muni.fi.pv256.movio2.uco_433419.data.CONTENT_AUTHORITY
import cz.muni.fi.pv256.movio2.uco_433419.data.FilmManager


/**
 * @author Tomáš Stolárik <tomas.stolarik@dactylgroup.com>
 */
class UpdaterSyncAdapter(context: Context, autoInitialize: Boolean) : AbstractThreadedSyncAdapter(context, autoInitialize) {

    override fun onPerformSync(account: Account, extras: Bundle, authority: String, provider: ContentProviderClient, syncResult: SyncResult) {
        try {
            val manager = FilmManager(context)
            val films = manager.getFilms()
            for (film in films) {
                film.popularity += 1
                manager.updateFilm(film)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {

        // Interval at which to sync with the server, in seconds.
        val SYNC_INTERVAL = 5 //day
        val SYNC_FLEXTIME = SYNC_INTERVAL / 3

        /**
         * Helper method to schedule the sync adapter periodic execution
         */
        fun configurePeriodicSync(context: Context, syncInterval: Int, flexTime: Int) {
            val account = getSyncAccount(context)
            val authority = context.getString(R.string.content_authority)
            // we can enable inexact timers in our periodic sync
            val request = SyncRequest.Builder()
                    .syncPeriodic(syncInterval.toLong(), flexTime.toLong())
                    .setSyncAdapter(account, authority)
                    .setExtras(Bundle.EMPTY) //enter non null Bundle, otherwise on some phones it crashes sync
                    .build()
            ContentResolver.requestSync(request)
        }

        /**
         * Helper method to have the sync adapter sync immediately
         *
         * @param context The context used to access the account service
         */
        fun syncImmediately(context: Context) {
            val bundle = Bundle()
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true)
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true)
            ContentResolver.requestSync(getSyncAccount(context), context.getString(R.string.content_authority), bundle)
        }

        fun initializeSyncAdapter(context: Context) {
            getSyncAccount(context)
        }

        /**
         * Helper method to get the fake account to be used with SyncAdapter, or make a new one if the
         * fake account doesn't exist yet.  If we make a new account, we call the onAccountCreated
         * method so we can initialize things.
         *
         * @param context The context used to access the account service
         * @return a fake account.
         */
        fun getSyncAccount(context: Context): Account? {
            // Get an instance of the Android account manager
            val accountManager = context.getSystemService(Context.ACCOUNT_SERVICE) as AccountManager

            // Create the account type and default account
            val newAccount = Account(context.getString(R.string.app_name), context.getString(R.string.account_type))

            // If the password doesn't exist, the account doesn't exist
            if (null == accountManager.getPassword(newAccount)) {

                /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
                if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                    return null
                }
                /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

                onAccountCreated(newAccount, context)
            }
            return newAccount
        }

        private fun onAccountCreated(newAccount: Account, context: Context) {
            UpdaterSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME)
            ContentResolver.setSyncAutomatically(newAccount, CONTENT_AUTHORITY, true)
            syncImmediately(context)
        }
    }
}