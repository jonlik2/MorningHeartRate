package com.jonliapps.morningheartratemonitor.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Pulse::class], version = 1, exportSchema = false)
abstract class PulseDatabase : RoomDatabase() {

    abstract val pulseDao: PulseDao

    companion object {
        @Volatile
        private var INSTANCE:PulseDatabase? = null

        fun getInstance(context: Context): PulseDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PulseDatabase::class.java,
                        "pulse_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}