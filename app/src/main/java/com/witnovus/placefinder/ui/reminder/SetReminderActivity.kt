package com.witnovus.placefinder.ui.reminder

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.witnovus.placefinder.R
import com.witnovus.placefinder.jobscheduler.SetJobService
import com.witnovus.placefinder.utils.Constants
import kotlinx.android.synthetic.main.activity_time_reminder.*

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

            if (hour != 0 || minute != 0) {

                if (!notificationmsg.text.toString().equals(Constants.BLANK_STRING)) {

                    hour = timePicker.hour * 3600000
                    minute = timePicker.minute * 60000
                    val milisec = hour + minute
                    //val renodom = Random()
                    val rendom = (0..5000).random()
                    /*Log.d("hour", "hour :" + hour)
                    Log.d("hour", "minute" + minute)
                    Log.d("hour", "milisec" + milisec)
                    Log.d("hour", "remodom" + rendom)*/
                    SetJobService(this, milisec.toLong(), notificationmsg.text.toString(), rendom)
                    setTimer()
                } else {
                    Toast.makeText(this, resources.getString(R.string.notification_msg), Toast.LENGTH_SHORT).show()
                    notificationmsg.requestFocus()
                }

            } else {
                Toast.makeText(this, resources.getString(R.string.timer_msg), Toast.LENGTH_SHORT).show()
            }
        }
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
