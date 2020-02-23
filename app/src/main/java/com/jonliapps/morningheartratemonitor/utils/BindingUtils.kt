package com.jonliapps.morningheartratemonitor.utils

import androidx.databinding.BindingConversion
import java.text.SimpleDateFormat
import java.util.*


@BindingConversion
fun convertDateToString(date: Date): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
    return dateFormat.format(date)
}