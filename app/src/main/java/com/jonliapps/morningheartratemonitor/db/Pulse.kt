package com.jonliapps.morningheartratemonitor.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "pulse_table")
data class Pulse(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    val value: Int = 0,
    val date: Date = Calendar.getInstance().time
)