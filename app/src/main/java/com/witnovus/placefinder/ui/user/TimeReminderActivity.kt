package com.witnovus.placefinder.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.witnovus.placefinder.R
import kotlinx.android.synthetic.main.activity_time_reminder.*

class TimeReminderActivity : AppCompatActivity() {

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
    }

   
}
