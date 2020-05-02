package com.witnovus.placefinder.ui.user

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.witnovus.placefinder.R
import com.witnovus.placefinder.databinding.ActivityRegistrationBinding
import com.witnovus.placefinder.model.UserModel
import com.witnovus.placefinder.ui.dashboard.DashboardActivity
import com.witnovus.placefinder.utils.AppUtils
import com.witnovus.placefinder.utils.Constants
import com.witnovus.placefinder.utils.CustomProgressDialog
import kotlinx.android.synthetic.main.activity_registration.*

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
        databaseReference = FirebaseDatabase.getInstance().reference.child(Constants.DB_KEY_USER)
        registrationDatabinding.signUpButton.setOnClickListener {
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
        if(AppUtils.isValidEmail(emailId)){
            textInputLayoutEmail.isErrorEnabled = false
        }else{
            textInputLayoutEmail.error = getString(R.string.str_enter_valid_email_id)
            textInputLayoutEmail.isErrorEnabled = true
            textInputLayoutEmail.requestFocus()
            return
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

        // progressbar call
        //  progressDialog.show(this)

        auth.createUserWithEmailAndPassword(emailId, password)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    Log.d("success", "linkWithCredential:success")
                    createNewUser(task.result!!.user!!)
                } else {
                    Log.w("Failure", "linkWithCredential:failure", task.exception)
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthUserCollisionException) {
                        textInputLayoutEmail.error =
                            getString(R.string.string_email_id_is_already_register)
                    } catch (e: FirebaseNetworkException) {
                        Toast.makeText(
                            applicationContext,
                            R.string.string_check_internet_conn_msg,
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            }
    }

    /*
     * store a user data to the firebase
     * */
    private fun createNewUser(firebaseUser: FirebaseUser): Unit {

        val email = firebaseUser.email
        val userId = firebaseUser.uid
        val userModel = UserModel(userName, mobileNo, email!!, userId)

        databaseReference.child(userId).setValue(userModel)

        var isComingFormSentInquiry = false

        if (intent.extras != null) {
            isComingFormSentInquiry =
                intent.getBooleanExtra(Constants.INTENT_IS_COMING_FROM_MY_CART, false)
        }
        if (!isComingFormSentInquiry) {
            val intent = Intent(this@RegistrationActivity, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        Toast.makeText(
            this@RegistrationActivity,
            getString(R.string.str_registration_successfully),
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }
}
