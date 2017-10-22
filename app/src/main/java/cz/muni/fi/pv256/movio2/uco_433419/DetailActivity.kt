package cz.muni.fi.pv256.movio2.uco_433419

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*


/**
 * Created by Tomas on 22. 10. 2017.
 */
class DetailActivity : AppCompatActivity(), DetailFragment.OnFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }

    override fun onStart() {
        super.onStart()
        val film = intent.getParcelableExtra<Film>("FILM")
        detailFragment.detailText.text = film.title
    }

    override fun onFragmentInteraction(uri: Uri) = Unit
}