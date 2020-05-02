package com.witnovus.placefinder.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.witnovus.placefinder.R
import com.witnovus.placefinder.databinding.ActivityDahbordBinding
import com.witnovus.placefinder.ui.reminder.SetReminderActivity
import com.witnovus.placefinder.ui.searchplace.SearchPlaceActivity


class DashboardActivity : AppCompatActivity() {

    private lateinit var dahbordBinding: ActivityDahbordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dahbordBinding = DataBindingUtil.setContentView(this, R.layout.activity_dahbord)

        dahbordBinding.btnMap.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SearchPlaceActivity::class.java)
            startActivity(intent)
        })

        dahbordBinding.btnReminder.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SetReminderActivity::class.java)
            startActivity(intent)
        })

    }
}
