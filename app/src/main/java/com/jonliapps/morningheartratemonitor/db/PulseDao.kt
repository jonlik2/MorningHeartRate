package com.jonliapps.morningheartratemonitor.db

import androidx.room.*

@Dao
abstract class PulseDao(private val database: PulseDatabase) {

    @Insert
    abstract suspend fun insert(pulse: Pulse)

    @Update
    abstract suspend fun update(pulse: Pulse)

    @Delete
    abstract suspend fun delete(pulse: Pulse)

    @Query("DELETE FROM pulse_table")
    abstract suspend fun clear()

    @Query("SELECT * FROM pulse_table WHERE id = :key")
    abstract suspend fun getPulse(key: Long): Pulse?

    @Query("SELECT * FROM pulse_table ORDER BY datetime(date) ASC")
    abstract suspend fun getAllPulse(): List<Pulse>

}