package cz.muni.fi.pv256.movio2.uco_433419

import android.app.FragmentManager
import android.app.FragmentTransaction
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), ListFragment.OnFragmentInteractionListener, DetailFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onFragmentInteraction(uri: Uri) = Unit
}
