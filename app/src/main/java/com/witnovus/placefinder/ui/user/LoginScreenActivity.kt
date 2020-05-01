package com.witnovus.placefinder.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.witnovus.placefinder.ui.dashboard.DashboardActivity
import com.witnovus.placefinder.R
import com.witnovus.placefinder.databinding.ActivityLoginScreenBinding

class LoginScreenActivity : AppCompatActivity() {


    private lateinit var loginDataBinding: ActivityLoginScreenBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var emailId: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_screen)

        auth = FirebaseAuth.getInstance()
        loginDataBinding.loginButton.setOnClickListener {
            getLoginData()
        }
    }
    private fun getLoginData(){

        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
    }
}
