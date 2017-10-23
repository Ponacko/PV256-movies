package cz.muni.fi.pv256.movio2.uco_433419

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.TextView
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity


/**
 * Created by Tomas on 22. 10. 2017.
 */
class FilmViewHolder(var filmView: TextView) : RecyclerView.ViewHolder(filmView), View.OnClickListener {

    init {
        filmView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        //Log.d("RecyclerView", "CLICK!")
    }
}