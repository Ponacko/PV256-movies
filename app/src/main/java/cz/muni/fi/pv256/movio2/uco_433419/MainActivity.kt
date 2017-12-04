package cz.muni.fi.pv256.movio2.uco_433419

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu


class MainActivity : AppCompatActivity(), ListFragment.OnFragmentInteractionListener, DetailFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.mainmenu, menu)
        val item = menu?.findItem(R.id.myswitch)
        item?.setActionView(R.layout.switch_layout)
        return true
    }

    override fun onFragmentInteraction(uri: Uri) = Unit
}
