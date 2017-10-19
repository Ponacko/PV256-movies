package cz.muni.fi.pv256.movio2.uco_433419

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by Tomas on 20. 10. 2017.
 */
class FilmAdapter(private var dataSet: ArrayList<Film>)  : RecyclerView.Adapter<FilmAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.filmView?.text = dataSet[position].getTitle()
    }

    override fun getItemCount(): Int = dataSet.count()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FilmAdapter.ViewHolder {
        val v = LayoutInflater.from(parent?.context)
                .inflate(R.layout.film_item, parent, false) as TextView
        return ViewHolder(v)
    }

    class ViewHolder(var filmView: TextView?) : RecyclerView.ViewHolder(filmView)
}