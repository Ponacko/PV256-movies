package cz.muni.fi.pv256.movio2.uco_433419.list

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.muni.fi.pv256.movio2.uco_433419.*
import cz.muni.fi.pv256.movio2.uco_433419.model.Film


/**
 * @author Tomáš Stolárik <tomas.stolarik@dactylgroup.com>
 */
class FilmDatabaseAdapter(private val fragment: ListFragment) :
        RecyclerViewCursorAdapter<Film, FilmDatabaseAdapter.FilmViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FilmDatabaseAdapter.FilmViewHolder {
        val v = LayoutInflater.from(parent?.context)
                .inflate(R.layout.film_item, parent, false) as FilmView
        return FilmViewHolder(v)
    }

    override fun onBindViewHolder(holder: FilmDatabaseAdapter.FilmViewHolder?, position: Int) {
        if (getItem(position) != null)
            (holder?.view as FilmView).setFilmProperties(getItem(position)!!)
    }

    override fun fromCursorRow(cursor: Cursor): Film =
            Film(cursor.getString(COL_FILM_ORIGINAL_TITLE),
                    cursor.getString(COL_FILM_RELEASE_DATE),
                    cursor.getFloat(COL_FILM_POPULARITY),
                    cursor.getString(COL_FILM_POSTER_PATH),
                    cursor.getString(COL_FILM_BACKDROP_PATH))

    inner class FilmViewHolder(var view: View) : RecyclerViewCursorAdapter.ViewHolder(view), View.OnClickListener {
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (getItem(position) != null)
                fragment.startFilmDetailActivity(getItem(position)!!)
        }
    }


}