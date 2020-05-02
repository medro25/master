package com.witnovus.placefinder.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.witnovus.placefinder.ui.dashboard.DashboardActivity
import com.witnovus.placefinder.R
import com.witnovus.placefinder.databinding.ActivityLoginScreenBinding
import com.witnovus.placefinder.utils.AppUtils
import kotlinx.android.synthetic.main.activity_login_screen.*

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

        loginDataBinding.LoginCreateNewUserTxtView.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        loginDataBinding.loginForgotPasswordTxtView.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getLoginData() {

        emailId = loginEmailIdEdtView.text.toString()
        password = loginPasswordEdtView.text.toString()

        if (TextUtils.isEmpty(emailId)) {
            emailTextInputLayout.error = getString(R.string.msg_enter_email_id)
            emailTextInputLayout.isErrorEnabled = true
            loginEmailIdEdtView.requestFocus()
            return
        } else {
            emailTextInputLayout.isErrorEnabled = false
        }
        if(AppUtils.isValidEmail(emailId)){
            emailTextInputLayout.isErrorEnabled = false
        }else{
            emailTextInputLayout.error = getString(R.string.str_enter_valid_email_id)
            emailTextInputLayout.isErrorEnabled = true
            loginEmailIdEdtView.requestFocus()
            return
        }
        if (TextUtils.isEmpty(password)) {
            loginPasswordInputLayout.error = getString(R.string.str_enter_a_password)
            loginPasswordInputLayout.isErrorEnabled = true
            loginPasswordEdtView.requestFocus()
            return
        } else {
            loginPasswordInputLayout.isErrorEnabled = false
        }
        if (password.length < 7) {
            loginPasswordEdtView.requestFocus()
            loginPasswordInputLayout.error = getString(R.string.msg_enter_at_least_8_char)
            loginPasswordInputLayout.isErrorEnabled = true
            return
        } else {
            loginPasswordInputLayout.isErrorEnabled = false
        }
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
    }
}
