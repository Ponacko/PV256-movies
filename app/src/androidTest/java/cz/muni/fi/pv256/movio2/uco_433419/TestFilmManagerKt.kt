package cz.muni.fi.pv256.movio2.uco_433419

/**
 * @author Tomáš Stolárik <tomas.stolarik></tomas.stolarik>@dactylgroup.com>
 */

import android.test.AndroidTestCase
import android.util.Log
import cz.muni.fi.pv256.movio2.uco_433419.data.FilmEntry
import cz.muni.fi.pv256.movio2.uco_433419.data.FilmManager
import cz.muni.fi.pv256.movio2.uco_433419.model.Film
import junit.framework.TestCase

class TestFilmManagerKt : AndroidTestCase() {

    private var manager: FilmManager? = null

    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        manager = FilmManager(mContext)
    }

    @Throws(Exception::class)
    public override fun tearDown() {
        super.tearDown()
        mContext.contentResolver.delete(
                FilmEntry.CONTENT_URI,
                null, null
        )
    }

    @Throws(Exception::class)
    fun testGetFilms() {

        val film1 = createFilm("Star Wars", "1977", "sw", "backdrop", 3.5f)
        val film2 = createFilm("Lord of the Rings", "2001", "lotr", "bnackdrop", 3.0f)
        val expectedFilms = arrayListOf(film1, film2)
        manager!!.createFilm(film1)
        manager!!.createFilm(film2)

        val films = manager!!.getFilms()
        Log.d(TAG, films.toString())
        TestCase.assertTrue("Size expected 2 but was ${films.size}", films.size == 2)
        TestCase.assertEquals(expectedFilms, films)
    }


    private fun createFilm(title: String, releaseDate: String, coverPath: String, backdropPath: String,
                           popularity: Float): Film =
            Film(title, releaseDate, popularity, coverPath, backdropPath)

    companion object {

        private val TAG = TestFilmManagerKt::class.java.simpleName
    }
}