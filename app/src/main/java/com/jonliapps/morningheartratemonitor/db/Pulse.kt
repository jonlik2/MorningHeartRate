package com.jonliapps.morningheartratemonitor.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jonliapps.morningheartratemonitor.utils.idGenerated
import java.util.*

@Entity(tableName = "pulse_table")
data class Pulse(
    @PrimaryKey
    var id: String = idGenerated,
    val value: Int = 0,
    val date: Date? = null
)