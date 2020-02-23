package com.jonliapps.morningheartratemonitor.utils

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun convertDateToTimestamp(date: Date?): Long? {
        date?.let {
            return date.time
        }
        return null
    }

    @TypeConverter
    fun convertTimestampToDate(timestamp: Long?): Date? {
        timestamp?.let {
            return Date(timestamp)
        }
        return null
    }

}