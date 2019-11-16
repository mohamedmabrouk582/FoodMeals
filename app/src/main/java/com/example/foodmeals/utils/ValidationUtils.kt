package com.example.foodmeals.utils

import android.text.TextUtils
import java.util.regex.Pattern


/*
* Created By mabrouk on 20/03/19
* KotilnApp
*/

class ValidationUtils {
    companion object {
        fun isEnglishLettersOnly(str: String): Boolean {
            val regex = "[a-z ]*"
            val p = java.util.regex.Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
            val m = p.matcher(str.trim { it <= ' ' })

            return !TextUtils.isEmpty(str.trim { it <= ' ' }) && m.matches()
        }

        fun isArabicLettersOnly(text: String): Boolean {
            val rejex = "[أ-ي ا-ى ًٍَُِّ~ْ]*"
            val p = java.util.regex.Pattern.compile(rejex, Pattern.CASE_INSENSITIVE)
            val m = p.matcher(text.trim { it <= ' ' })

            return m.matches()
        }

        fun isEnglishTextExist(str: String): Boolean {
            val regex = "[a-z]+"
            val p = java.util.regex.Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
            val m = p.matcher(str.trim { it <= ' ' })

            return m.find()
        }

        fun isArabicTextExist(str: String): Boolean {
            val regex = "[أ-ي١-٩ا-ى]+"
            val p = java.util.regex.Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
            val m = p.matcher(str.trim { it <= ' ' })

            return m.find()
        }

        fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidPassword(password: String): Boolean {
            val ePattern = "(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,}$"
            val p = java.util.regex.Pattern.compile(ePattern, Pattern.CASE_INSENSITIVE)
            val m = p.matcher(password)
            return !TextUtils.isEmpty(password.trim { it <= ' ' }) && m.matches()
        }

        fun isValidEgyptianMobileNumber(phone: String): Boolean {
            val pattern = "^(01)(1||2||5||0)[0-9]{8}"
            val p = java.util.regex.Pattern.compile(pattern)
            val m = p.matcher(phone.trim { it <= ' ' })
            return m.matches() && phone.length == 11
        }

        fun isEnglishNumbers(number: String): Boolean {
            val pattern = "[0-9]*"
            val p = java.util.regex.Pattern.compile(pattern)
            val m = p.matcher(number)
            return TextUtils.isEmpty(number.trim { it <= ' ' }) || m.matches()
        }

        fun isArabicNumbers(number: String): Boolean {
            val pattern = "[٠-٩]*"
            val p = java.util.regex.Pattern.compile(pattern)
            val m = p.matcher(number)
            return TextUtils.isEmpty(number.trim { it <= ' ' }) || m.matches()
        }
    }
}
