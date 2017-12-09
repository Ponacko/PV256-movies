package cz.muni.fi.pv256.movio2.uco_433419

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.view.Menu
import android.view.MenuItem
import cz.muni.fi.pv256.movio2.uco_433419.detail.DetailFragment
import cz.muni.fi.pv256.movio2.uco_433419.list.ListFragment
import cz.muni.fi.pv256.movio2.uco_433419.synchronization.UpdaterSyncAdapter


class MainActivity : AppCompatActivity(), ListFragment.OnFragmentInteractionListener,
        DetailFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        UpdaterSyncAdapter.initializeSyncAdapter(this)
        //UpdaterSyncAdapter.configurePeriodicSync(this, 5, 5)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.mainmenu, menu)
        val item = menu?.findItem(R.id.myswitch)
        item?.setActionView(R.layout.switch_layout)
        (item?.actionView?.findViewById(R.id.switchForActionBar) as SwitchCompat).setOnCheckedChangeListener { _, isChecked ->
            val listFragment = supportFragmentManager.findFragmentById(R.id.listFragment) as ListFragment
            if (isChecked) {
                listFragment.switchToDatabase()
            } else {
                listFragment.switchToNetwork()
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.refresh) {
            UpdaterSyncAdapter.syncImmediately(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFragmentInteraction(uri: Uri) = Unit
}
