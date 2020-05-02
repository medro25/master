package com.witnovus.placefinder.ui.user

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.witnovus.placefinder.R
import com.witnovus.placefinder.databinding.ActivityForgotPasswordBinding
import com.witnovus.placefinder.utils.AppUtils
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var forgotPasswordBinding: ActivityForgotPasswordBinding

    private lateinit var emailId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //set support action bar for a display back icon
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        forgotPasswordBinding.resetPasswordButton.setOnClickListener {
            resetPassword()
        }

    }

    private fun resetPassword() {
        emailId = forgotPasswordEmailIdEdtView.text.toString()

        if (TextUtils.isEmpty(emailId)) {
            textInputLayoutEmail.error = getString(R.string.msg_enter_email_id)
            textInputLayoutEmail.isErrorEnabled = true
            return
        } else {
            textInputLayoutEmail.isErrorEnabled = false
        }
        if (AppUtils.isValidEmail(emailId)){
            textInputLayoutEmail.isErrorEnabled = false
        }else{
            textInputLayoutEmail.error = getString(R.string.str_enter_valid_email_id)
            textInputLayoutEmail.isErrorEnabled = true
            return
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
