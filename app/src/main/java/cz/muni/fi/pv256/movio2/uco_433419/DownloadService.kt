package cz.muni.fi.pv256.movio2.uco_433419

import android.app.IntentService
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author Tomáš Stolárik <tomas.stolarik@dactylgroup.com>
 */
class DownloadService : IntentService("Download service") {
    private val client = OkHttpClient()
    private lateinit var notificationManager: NotificationManager


    override fun onHandleIntent(p0: Intent?) {
        sendNotification("Film data is being downloaded.", 0)
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
            sendNotification("Data was downloaded succesfully.", 1)

        } catch (e: Exception) {
            sendBroadcastIntent("")
            sendNotification("There was an error while downloading data.", 1)
        } finally {
            notificationManager.cancel(0)
        }
    }

    private fun sendNotification(msg: String, id: Int) {
        notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val contentIntent = PendingIntent.getActivity(this, 0,
                Intent(this, MainActivity::class.java), 0)

        val builder = NotificationCompat.Builder(this)
                .setContentTitle("Films")
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(msg))
                .setContentText(msg)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentIntent(contentIntent)
        notificationManager.notify(id, builder.build())
    }

    private fun sendBroadcastIntent(responseBody: String) {
        val intent = Intent()
        intent.action = ACTION_DOWNLOAD_DATA
        intent.putExtra("result", responseBody)
        sendBroadcast(intent)
    }
}