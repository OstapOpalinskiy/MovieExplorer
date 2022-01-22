package com.opalynskyi.utils

import java.text.SimpleDateFormat
import java.util.*

class DateTimeHelper {

    fun getTimestampFrom(stringDate: String): Long {
        val date = dateFormatter.parse(stringDate)
        return date?.time!!
    }

    fun getMonthFromToday(numberMonth: Int): Long {
        val cal = Calendar.getInstance()
        cal.add(Calendar.MONTH, -numberMonth)
        return cal.timeInMillis
    }

    fun getServerDate(millis: Long): String {
        return getStringDate(millis, SERVER_DATE_FORMAT)
    }

    fun getHeaderDate(millis: Long): String {
        return getStringDate(millis, HEADER_DATE_FORMAT)
    }

    private fun getStringDate(millis: Long, pattern: String): String {
        val date = Date(millis)
        dateFormatter.applyPattern(pattern)
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
        private const val HEADER_DATE_FORMAT = "MMMM yyyy"
        private val dateFormatter = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.US)
    }
}