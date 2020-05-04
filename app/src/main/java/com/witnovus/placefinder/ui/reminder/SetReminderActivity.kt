package com.witnovus.placefinder.ui.reminder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.allyants.notifyme.NotifyMe
import com.witnovus.placefinder.R
import com.witnovus.placefinder.utils.Constants
import kotlinx.android.synthetic.main.activity_time_reminder.*
import java.util.*

class SetReminderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_reminder)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //set support action bar for a display back icon
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        timePicker.hour = 0
        timePicker.minute = 0
        timePicker.setIs24HourView(true)



        setReminderButton.setOnClickListener {

            var hour = timePicker.hour * 3600000
            var minute = timePicker.minute * 60000
            var sec :Int

            if (hour != 0 || minute != 0) {

                if (!notificationmsg.text.toString().equals(Constants.BLANK_STRING)) {

                    /*hour = timePicker.hour * 3600000
                    minute = timePicker.minute * 60000
                    val milisec = hour + minute*/
                    //val renodom = Random()

                    hour = timePicker.hour
                    minute = timePicker.minute
                    //sec=timePicker

                    val rendom = (0..5000).random()
                    /*Log.d("hour", "hour :" + hour)
                    Log.d("hour", "minute" + minute)
                    Log.d("hour", "milisec" + milisec)
                    Log.d("hour", "remodom" + rendom)*/
                    setNotification(hour,minute, notificationmsg.text.toString())
                    //SetJobService(this, milisec.toLong(), notificationmsg.text.toString(), rendom)
                    setTimer()
                } else {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.notification_msg),
                        Toast.LENGTH_SHORT
                    ).show()
                    notificationmsg.requestFocus()
                }

            } else {
                Toast.makeText(this, resources.getString(R.string.timer_msg), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setNotification(hour: Int, minute: Int, notificationMasssage: String) {
        var now = Calendar.getInstance()
        now.add(Calendar.HOUR_OF_DAY,hour)
        now.add(Calendar.MINUTE,minute)

        var notifyMe: NotifyMe.Builder = NotifyMe.Builder(getApplicationContext())
        notifyMe .title("Place Reminder")
        notifyMe .content(notificationMasssage)
        notifyMe .color(0,255,0,255)
        notifyMe .led_color(255,255,255,255)
        notifyMe.time(now)
        notifyMe .key("test")
        notifyMe .addAction(Intent(),"Dismiss",true,false)
        notifyMe .large_icon(R.mipmap.ic_launcher)
        notifyMe .rrule("FREQ=MINUTELY;INTERVAL=5;COUNT=2")
        notifyMe .build()

        Toast.makeText(this,resources.getString(R.string.lbl_reminder),Toast.LENGTH_SHORT).show()
    }

    private fun setTimer() {

        notificationmsg.setText(Constants.BLANK_STRING)
        timePicker.hour = 0
        timePicker.minute = 0
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
