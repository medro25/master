package com.witnovus.placefinder.ui.user

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.witnovus.placefinder.R
import com.witnovus.placefinder.databinding.ActivityRegistrationBinding
import com.witnovus.placefinder.utils.CustomProgressDialog
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.progressbar.*

class RegistrationActivity : AppCompatActivity() {


    private lateinit var userName: String
    private lateinit var emailId: String
    private lateinit var password: String
    private lateinit var mobileNo: String

    val progressDialog = CustomProgressDialog()

    private lateinit var registrationDatabinding: ActivityRegistrationBinding

    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registrationDatabinding =
            DataBindingUtil.setContentView(this, R.layout.activity_registration)

        auth = FirebaseAuth.getInstance()
        /*
         *declaration of firebase  databasereference
         */
        databaseReference = FirebaseDatabase.getInstance().reference.child("users")
        registrationDatabinding.signUpButton.setOnClickListener {
            progressDialog.show(this)
            getData()
        }

        registrationDatabinding.alreadyRegisterTxtView.setOnClickListener {
            val intent = Intent(this, LoginScreenActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getData() {

        userName = userNameEdtView.text.toString()
        emailId = emailEdtView.text.toString()
        password = passwordEdtView.text.toString()
        mobileNo = mobileNoEdtView.text.toString()

        if (TextUtils.isEmpty(userName)) {
            textInputLayoutUsername.error = getString(R.string.msg_enter_full_name)
            textInputLayoutUsername.isErrorEnabled = true
            userNameEdtView.requestFocus()
            return
        } else {
            textInputLayoutUsername.isErrorEnabled = false
        }
        if (TextUtils.isEmpty(mobileNo)) {
            textInputLayoutMobileNo.error = getString(R.string.msg_enter_contact_number)
            textInputLayoutMobileNo.isErrorEnabled = true
            mobileNoEdtView.requestFocus()
            return
        } else {
            textInputLayoutMobileNo.isErrorEnabled = false
        }
        if (TextUtils.isEmpty(emailId)) {
            textInputLayoutEmail.error = getString(R.string.msg_enter_email_id)
            textInputLayoutEmail.isErrorEnabled = true
            emailEdtView.requestFocus()
            return
        } else {
            textInputLayoutEmail.isErrorEnabled = false
        }
        if (TextUtils.isEmpty(password)) {
            textInputLayoutPassword.error = getString(R.string.msg_enter_email_id)
            textInputLayoutPassword.isErrorEnabled = true
            passwordEdtView.requestFocus()
            return
        } else {
            textInputLayoutPassword.isErrorEnabled = false
        }
        if (password.length < 7) {
            passwordEdtView.requestFocus()
            textInputLayoutPassword.error = getString(R.string.msg_enter_at_least_8_char)
            textInputLayoutPassword.isErrorEnabled = true
            return
        } else {
            textInputLayoutPassword.isErrorEnabled = false
        }

    }
}
