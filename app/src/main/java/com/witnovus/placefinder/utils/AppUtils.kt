package com.witnovus.placefinder.utils

import android.text.TextUtils
import android.util.Patterns
import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import com.witnovus.placefinder.R



class AppUtils {


    companion object{

        fun showDialogMessage(context: Context, message: String) {
            val dialogBuilder = Dialog(context, R.style.DialogTheme)
            dialogBuilder.setContentView(R.layout.show_dialog);

            dialogBuilder.setCancelable(false)

            val text = dialogBuilder.findViewById(R.id.textviewMessage) as TextView
            text.setText(message)

            val dialogButton = dialogBuilder.findViewById(R.id.dialogOkButton) as Button
            dialogButton.setOnClickListener(View.OnClickListener { dialogBuilder.dismiss() })

            dialogBuilder.show()

        }

        /**
         * This function checks internet connection is available or not.
         * @param context : Instance of Context or Activity
         * @return Boolean
         *
         */
        fun checkConnection(@NonNull context: Context): Boolean {
            return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null
        }

        fun isValidEmail(target: CharSequence?): Boolean {
            return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target!!).matches()
        }

    }
}