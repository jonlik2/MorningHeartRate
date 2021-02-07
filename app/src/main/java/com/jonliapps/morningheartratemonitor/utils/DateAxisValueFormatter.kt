package com.jonliapps.morningheartratemonitor.utils

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DateAxisValueFormatter : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        val dateFormat = SimpleDateFormat("dd.MM", Locale.ENGLISH)
        val date = Date(value.toLong())
        return dateFormat.format(date)
    }

}