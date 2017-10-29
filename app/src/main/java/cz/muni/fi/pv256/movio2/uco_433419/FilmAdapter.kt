package cz.muni.fi.pv256.movio2.uco_433419

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class FilmAdapter(private val dataSet: ArrayList<ListItem>, private val fragment: ListFragment)  : RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {
    private val TYPE_FILM = 0
    private val TYPE_CATEGORY = 1

    override fun onBindViewHolder(holder: FilmViewHolder?, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == TYPE_CATEGORY){
            (holder?.view as TextView).text = dataSet[position].title
        }
        else {
            (holder?.view as FilmView).setFilmNameAndImage(dataSet[position].title, (dataSet[position] as Film).coverPath)
        }

    }

    override fun getItemCount(): Int = dataSet.count()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FilmViewHolder {
        return if (viewType == TYPE_CATEGORY){
            val v = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.category_item, parent, false) as TextView
            FilmViewHolder(v)
        }
        else {
            val v = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.film_item, parent, false) as FilmView
            FilmViewHolder(v)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (dataSet[position] is Film)
            TYPE_FILM
        else
            TYPE_CATEGORY
    }

    inner class FilmViewHolder(var view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (getItemViewType(layoutPosition) == TYPE_FILM){
                fragment.startFilmDetailActivity(dataSet[layoutPosition] as Film)
            }
        }
    }
}