package com.witnovus.placefinder.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.witnovus.placefinder.ui.dashboard.DashboardActivity
import com.witnovus.placefinder.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* window.requestFeature(Window.FEATURE_NO_TITLE)
         //making this activity full screen
         window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)*/
         setContentView(R.layout.activity_splash_screen)
        //Animation
        addAnimationOperations()
    }


      private fun addAnimationOperations() {
          Handler().postDelayed({ gotoNextActivity() }, 3000)
          val constraint1 = ConstraintSet()
          constraint1.clone(findViewById<ConstraintLayout>(R.id.root))

          val constraint2 = ConstraintSet()
          constraint2.clone(this, R.layout.splash_screen_alt)

          Handler().postDelayed({
              val autoTransition = AutoTransition()
              autoTransition.duration = 2000
              TransitionManager.beginDelayedTransition(
                  findViewById<ConstraintLayout>(R.id.root),
                  autoTransition
              )
              constraint2.applyTo(findViewById(R.id.root))
          }, 500)
      }

    private fun gotoNextActivity() {
        Handler().postDelayed({
            //start main activity
            startActivity(Intent(this@SplashScreenActivity, DashboardActivity::class.java))
            //finish this activity
            finish()
        }, 5000)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
    }
}
