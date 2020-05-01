package com.mobile_app.themovie.presentation.util

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class DateFormatter(private val locale: Locale) {

    fun convertFormat(
        source: String,
        converted: String = YYYYHMMHdd,
        formatted: String = ddSMMMCSyyyy
    ): String? {
        val sdf = SimpleDateFormat(converted, locale)
        val convertedDate: Date?
        var formattedDate: String? = null
        try {
            convertedDate = sdf.parse(source)
            formattedDate =
                SimpleDateFormat(formatted, locale).format(
                    convertedDate
                )
        } catch (e: ParseException) {
            Log.i(TAG,"Failed to convert ISOTimeToDate: " + e.localizedMessage)
        }
        return formattedDate
    }

    /**
     * @Sl = Slash
     * @S = Space
     * @C = Comma
     * @H = Hyphen
     */
    companion object {
        private const val YYYYHMMHdd = "yyyy-MM-dd"
        private const val ddSMMMCSyyyy = "dd MMM, yyyy"
        private val TAG = DateFormatter::class.java.simpleName
    }
}