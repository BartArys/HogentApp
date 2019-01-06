package bartarys.github.io.hogentapp.day

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bartarys.github.io.hogentapp.R
import bartarys.github.io.hogentapp.day.detail.DayDetailActivity
import bartarys.github.io.hogentapp.persistence.models.Day
import org.jetbrains.anko.UI
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Build
import android.view.MenuInflater
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import bartarys.github.io.hogentapp.day.review.DayReviewActivity
import bartarys.github.io.hogentapp.settings.SettingsActivity


class DayActivity : AppCompatActivity(), DayListCallback {

    companion object {
        const val CHANNEL_ID = "review channel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_list, DayListFragment.newInstance())
                .commitNow()
        }
    }

    override fun onDayClicked(day: Day) {
        Intent(this@DayActivity, DayDetailActivity::class.java)
            .let {
                val bundle = Bundle()
                bundle.putLong(DayDetailActivity.dayKey, day.id)
                it.putExtras(bundle)
                startActivity(it)
            }
    }

    override fun review() {
        Intent(this, DayReviewActivity::class.java)
            .let(this::startActivity)
    }

    override fun settings() {
        Intent(this, SettingsActivity::class.java)
            .let(this::startActivity)
    }



    //https://developer.android.com/training/notify-user/build-notification
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

fun startAlarmBroadcastReceiver(context: Context, delay: Long) {
    val intent = Intent(context, ReviewReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    // Remove any previous pending intent.
    alarmManager.cancel(pendingIntent)
    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent)
}


class ReviewReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val intent = Intent(context, DayReviewActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)


        val mBuilder = NotificationCompat.Builder(context!!, DayActivity.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_refresh_black_24dp)
            .setContentTitle("Review your day")
            .setContentText("Your day has ended, let's review your goals")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)

        val notification = mBuilder.build()
        NotificationManagerCompat.from(context).notify(0, notification)
    }


}