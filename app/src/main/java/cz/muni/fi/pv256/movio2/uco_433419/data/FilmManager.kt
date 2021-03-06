package cz.muni.fi.pv256.movio2.uco_433419.data

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import cz.muni.fi.pv256.movio2.uco_433419.model.Film


/**
 * @author Tomáš Stolárik <tomas.stolarik@dactylgroup.com>
 */
class FilmManager(context: Context) {
    val COL_FILM_ID = 0
    val COL_FILM_ORIGINAL_TITLE = 1
    val COL_FILM_RELEASE_DATE = 2
    val COL_FILM_POPULARITY = 3
    val COL_FILM_POSTER_PATH = 4
    val COL_FILM_BACKDROP_PATH = 5
    val COL_FILM_OVERVIEW = 6
    private val FILM_COLUMNS = arrayOf(FilmEntry._ID, FilmEntry.COLUMN_ORIGINAL_TITLE_TEXT,
            FilmEntry.COLUMN_RELEASE_DATE_TEXT, FilmEntry.COLUMN_POPULARITY_TEXT,
            FilmEntry.COLUMN_POSTER_PATH_TEXT, FilmEntry.COLUMN_BACKDROP_PATH_TEXT,
            FilmEntry.COLUMN_OVERVIEW_TEXT)

    private val LOCAL_DATE_FORMAT = "yyyyMMdd"

    private val WHERE_ID = FilmEntry._ID + " = ?"
    private val WHERE_NAME_AND_DATE = FilmEntry.COLUMN_ORIGINAL_TITLE_TEXT + " = ? AND " +
            FilmEntry.COLUMN_RELEASE_DATE_TEXT + " = ?"

    private var context: Context = context.applicationContext

    fun createFilm(film: Film?): Long? {
        if (film == null) {
            throw IllegalStateException("film cannot be null")
        }
        return ContentUris.parseId(context.contentResolver.insert(FilmEntry.CONTENT_URI,
                prepareFilmValues(film)))
    }

    fun containsFilm(film: Film): Boolean {
        val cursor = context.contentResolver.query(FilmEntry.CONTENT_URI, FILM_COLUMNS, WHERE_NAME_AND_DATE,
                arrayOf(film.original_title, film.release_date), null)
        val result = cursor.count > 0
        cursor.close()
        return result
    }

    fun getFilms(): List<Film> {
        val cursor = context.contentResolver.query(FilmEntry.CONTENT_URI, FILM_COLUMNS, null,
                null, null)
        if (cursor != null && cursor.moveToFirst()) {
            val films = arrayListOf<Film>()
            cursor.use {
                while (!it.isAfterLast) {
                    films.add(getFilm(it))
                    it.moveToNext()
                }
            }
            cursor.close()
            return films
        }

        return emptyList()
    }

    fun updateFilm(film: Film?) {
        checkNulls(film)
        context.contentResolver.update(FilmEntry.CONTENT_URI, prepareFilmValues(film!!), WHERE_ID,
                arrayOf(film.id.toString()))
    }

    private fun checkNulls(film: Film?) {
        if (film == null) {
            throw NullPointerException("film == null")
        }
    }

    fun deleteFilm(film: Film?) {
        if (film == null) {
            throw NullPointerException("film == null")
        }
        context.contentResolver.delete(FilmEntry.CONTENT_URI, WHERE_NAME_AND_DATE,
                arrayOf(film.original_title, film.release_date))
    }

    private fun prepareFilmValues(film: Film): ContentValues {
        val values = ContentValues()
        values.put(FilmEntry._ID, film.id)
        values.put(FilmEntry.COLUMN_ORIGINAL_TITLE_TEXT, film.original_title)
        values.put(FilmEntry.COLUMN_RELEASE_DATE_TEXT, film.release_date)
        values.put(FilmEntry.COLUMN_POPULARITY_TEXT, film.popularity)
        values.put(FilmEntry.COLUMN_POSTER_PATH_TEXT, film.poster_path)
        values.put(FilmEntry.COLUMN_BACKDROP_PATH_TEXT, film.backdrop_path)
        values.put(FilmEntry.COLUMN_OVERVIEW_TEXT, film.overview)

        return values
    }

    private fun getFilm(cursor: Cursor): Film {
        val film = Film(
                cursor.getLong(COL_FILM_ID),
                cursor.getString(COL_FILM_ORIGINAL_TITLE),
                cursor.getString(COL_FILM_RELEASE_DATE),
                cursor.getFloat(COL_FILM_POPULARITY),
                cursor.getString(COL_FILM_POSTER_PATH),
                cursor.getString(COL_FILM_BACKDROP_PATH),
                cursor.getString(COL_FILM_OVERVIEW)
        )
        film.id = cursor.getLong(COL_FILM_ID)
        return film
    }

}