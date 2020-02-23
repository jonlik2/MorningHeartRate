package com.jonliapps.morningheartratemonitor.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jonliapps.morningheartratemonitor.utils.Converters

@Database(
    entities = [Pulse::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PulseDatabase : RoomDatabase() {

    // DAO
    abstract fun pulseDao(): PulseDao

}