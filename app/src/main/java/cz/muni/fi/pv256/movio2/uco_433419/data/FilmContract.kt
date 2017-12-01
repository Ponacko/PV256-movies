package cz.muni.fi.pv256.movio2.uco_433419.data

/**
 * @author Tomáš Stolárik <tomas.stolarik@dactylgroup.com>
 */
import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat


val CONTENT_AUTHORITY = "cz.muni.fi.pv256.movio2.uco_433419"
val BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY)!!
val PATH_WORK_TIME = "film"

val DATE_FORMAT = "yyyyMMddHHmm"

/**
 * Converts Date class to a string representation, used for easy comparison and database
 * lookup.
 *
 * @param date The input date
 * @return a DB-friendly representation of the date, using the format defined in DATE_FORMAT.
 */
fun getDbDateString(date: DateTime): String = date.toString(DATE_FORMAT)

/**
 * Converts a dateText to a long Unix time representation
 *
 * @param dateText the input date string
 * @return the Date object
 */
fun getDateFromDb(dateText: String): DateTime =
        DateTime.parse(dateText, DateTimeFormat.forPattern(DATE_FORMAT).withOffsetParsed())

class FilmEntry : BaseColumns {
    companion object {

        val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORK_TIME).build()

        val CONTENT_TYPE = "vnd.android.cursor.dir/$CONTENT_AUTHORITY/$PATH_WORK_TIME"
        val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/$CONTENT_AUTHORITY/$PATH_WORK_TIME"

        val TABLE_NAME = "film"
        val _ID = BaseColumns._ID
        val COLUMN_ORIGINAL_TITLE_TEXT = "original_title"
        val COLUMN_RELEASE_DATE_TEXT = "release_date"
        val COLUMN_POPULARITY_TEXT = "popularity"
        val COLUMN_POSTER_PATH_TEXT = "poster_path"
        val COLUMN_BACKDROP_PATH_TEXT = "backdrop_path"

        fun buildFilmUri(id: Long): Uri = ContentUris.withAppendedId(CONTENT_URI, id)
    }
}
