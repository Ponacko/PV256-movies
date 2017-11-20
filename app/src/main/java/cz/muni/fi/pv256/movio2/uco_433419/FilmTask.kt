package cz.muni.fi.pv256.movio2.uco_433419

import android.os.AsyncTask
import com.google.gson.GsonBuilder
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import java.lang.ref.WeakReference

/**
 * @author Tomáš Stolárik <tomas.stolarik@dactylgroup.com>
 */
class FilmTask(fragment: ListFragment) : AsyncTask<Void, Int, String>() {
    private val fragmentReference: WeakReference<ListFragment>? = WeakReference(fragment)
    private val client = OkHttpClient()

    override fun doInBackground(vararg p0: Void?): String {
        val request = Request.Builder()
                .url("https://api.themoviedb.org/3/discover/movie?api_key=8009a08149f286e1ef6f22da18708ae4&primary_release_date.gte=2014-09-15&primary_release_date.lte=2014-10-22")
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