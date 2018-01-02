package cz.muni.fi.pv256.movio2.uco_433419.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

/**
 * @author Tomáš Stolárik <tomas.stolarik@dactylgroup.com>
 */
class FilmDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + FilmEntry.TABLE_NAME + " (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY," +
                FilmEntry.COLUMN_ORIGINAL_TITLE_TEXT + " TEXT NOT NULL, " +
                FilmEntry.COLUMN_BACKDROP_PATH_TEXT + " TEXT," +
                FilmEntry.COLUMN_POPULARITY_TEXT + " TEXT," +
                FilmEntry.COLUMN_POSTER_PATH_TEXT + " TEXT," +
                FilmEntry.COLUMN_RELEASE_DATE_TEXT + " TEXT);"
        db.execSQL(SQL_CREATE_LOCATION_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + FilmEntry.TABLE_NAME)
        onCreate(db)
    }

    companion object {

        val DATABASE_NAME = "film.db"
        private val DATABASE_VERSION = 1
    }
}