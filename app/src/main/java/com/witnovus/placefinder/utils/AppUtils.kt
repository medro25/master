package com.witnovus.placefinder.utils

import android.text.TextUtils
import android.util.Patterns

class AppUtils {

    companion object{
        fun isValidEmail(target: CharSequence?): Boolean {
            return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target!!).matches()
        }

    }


}