package com.jonliapps.morningheartratemonitor.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PulseDao {

    @Insert
    fun insert(pulse: Pulse)

    @Update
    fun update(pulse: Pulse)

    @Delete
    fun delete(pulse: Pulse)

    @Query("DELETE FROM pulse_table")
    fun clear()

    @Query("SELECT * FROM pulse_table WHERE id = :key")
    fun getPulse(key: Long): Pulse?

    @Query("SELECT * FROM pulse_table ORDER BY date DESC")
    fun getAllPulse(): LiveData<List<Pulse>>
}