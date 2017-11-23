package cz.muni.fi.pv256.movio2.uco_433419

import android.os.AsyncTask
import com.google.gson.GsonBuilder
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Tomáš Stolárik <tomas.stolarik@dactylgroup.com>
 */
class FilmTask(fragment: ListFragment) : AsyncTask<Void, Int, String>() {
    private val fragmentReference: WeakReference<ListFragment>? = WeakReference(fragment)
    private val client = OkHttpClient()

    override fun doInBackground(vararg p0: Void?): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_MONTH, -7)
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val sevenDaysAgo = formatter.format(cal.time)
        cal.add(Calendar.DAY_OF_MONTH, 14)
        val sevenDaysFromNow = formatter.format(cal.time)
        val request = Request.Builder()
                .url("https://api.themoviedb.org/3/discover/movie?api_key=8009a08149f286e1ef6f22da18708ae4&primary_release_date.gte=$sevenDaysAgo&primary_release_date.lte=$sevenDaysFromNow?sort_by=primary_release_date.desc")
                .build()
        try {
            val response = client.newCall(request).execute()
            return response.body().string()
        } catch (e: Exception) {
            e.printStackTrace();
        }
        return ""
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        try {
            if (result != null && result != "") {
                val gson = GsonBuilder().create()
                val r = gson.fromJson(result, FilmResponse::class.java)
                fragmentReference?.get()?.addResponseToFilmList(r)
            }
        } catch (e: Exception) {
            // TODO
        }
    }
}