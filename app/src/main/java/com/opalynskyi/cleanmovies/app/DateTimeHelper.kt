package com.opalynskyi.cleanmovies.app

import java.text.SimpleDateFormat
import java.util.*

class DateTimeHelper {

    fun getTimestampFrom(stringDate: String): Long {
        val date = dateFormatter.parse(stringDate)
        return date.time
    }

    fun getMonthFromToday(numberMonth: Int): Long {
        val cal = Calendar.getInstance()
        cal.add(Calendar.MONTH, -numberMonth)
        return cal.timeInMillis
    }

    fun getStringDate(millis: Long): String {
        val date = Date(millis)
        return dateFormatter.format(date)
    }

    fun getYear(millis: Long): Int {
        val cal = Calendar.getInstance()
        cal.timeInMillis = millis
        return cal.get(Calendar.YEAR)
    }

    fun getMonth(millis: Long): Int {
        val cal = Calendar.getInstance()
        cal.timeInMillis = millis
        return cal.get(Calendar.MONTH)
    }

    companion object {
        private const val SERVER_DATE_FORMAT = "yyyy-MM-dd"
        private val dateFormatter = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.US)
    }
}