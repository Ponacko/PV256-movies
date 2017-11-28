package cz.muni.fi.pv256.movio2.uco_433419

import android.app.IntentService
import android.content.Intent
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Tomáš Stolárik <tomas.stolarik@dactylgroup.com>
 */
class DownloadService : IntentService("Download service") {
    private val client = OkHttpClient()


    override fun onHandleIntent(p0: Intent?) {
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
            sendBroadcastIntent(response.body().string())

        } catch (e: Exception) {
            sendBroadcastIntent("")
        }

    }

    private fun sendBroadcastIntent(responseBody: String) {
        val intent = Intent()
        intent.action = ACTION_DOWNLOAD_DATA
        intent.putExtra("result", responseBody)
        sendBroadcast(intent)
    }
}