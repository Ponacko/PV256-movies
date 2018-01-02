package cz.muni.fi.pv256.movio2.uco_433419.download

import android.app.IntentService
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.support.v4.app.NotificationCompat
import cz.muni.fi.pv256.movio2.uco_433419.ACTION_DOWNLOAD_DATA
import cz.muni.fi.pv256.movio2.uco_433419.MainActivity
import cz.muni.fi.pv256.movio2.uco_433419.R
import cz.muni.fi.pv256.movio2.uco_433419.model.Film
import cz.muni.fi.pv256.movio2.uco_433419.model.FilmResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author Tomáš Stolárik
 */
class DownloadService : IntentService("Download service") {
    private lateinit var notificationManager: NotificationManager

    override fun onHandleIntent(p0: Intent?) {
        sendNotification("Film data is being downloaded.", 0)
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_MONTH, -7)
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val sevenDaysAgo = formatter.format(cal.time)
        cal.add(Calendar.DAY_OF_MONTH, 14)
        val sevenDaysFromNow = formatter.format(cal.time)
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create<DiscoverService>(DiscoverService::class.java)
        val films = service.listFilms(sevenDaysAgo, sevenDaysFromNow)
        films.request().url().toString()
        films.enqueue(object : Callback<FilmResponse> {
            override fun onFailure(call: Call<FilmResponse>?, t: Throwable?) {
                downloadFailure()
            }

            override fun onResponse(call: Call<FilmResponse>?, response: Response<FilmResponse>?) =
                    if (response?.body()?.results != null) {
                        sendBroadcastIntent(response.body()!!.results as ArrayList<Film>)
                        sendNotification(getString(R.string.notification_successful), 1)
                    } else {
                        downloadFailure()
                    }
        })
        notificationManager.cancel(0)
    }

    private fun downloadFailure() {
        sendBroadcastIntent(arrayListOf())
        sendNotification(getString(R.string.notification_error), 1)
    }

    private fun sendNotification(msg: String, id: Int) {
        notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val contentIntent = PendingIntent.getActivity(this, 0,
                Intent(this, MainActivity::class.java), 0)

        val builder = NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.notification_title))
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(msg))
                .setContentText(msg)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentIntent(contentIntent)
        notificationManager.notify(id, builder.build())
    }

    private fun sendBroadcastIntent(responseBody: ArrayList<Film>) {
        val intent = Intent()
        intent.action = ACTION_DOWNLOAD_DATA
        intent.putParcelableArrayListExtra("result", responseBody)
        sendBroadcast(intent)
    }
}