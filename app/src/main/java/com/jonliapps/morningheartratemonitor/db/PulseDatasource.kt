package com.jonliapps.morningheartratemonitor.db

import com.jonliapps.morningheartratemonitor.utils.Result

class PulseDatasource(private val pulseDao: PulseDao) {

    suspend fun save(pulse: Pulse): Result<Void?> {
        return try {
            pulseDao.insert(pulse)
            Result.Success(null)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun delete(pulse: Pulse): Result<Void?> {
        return try {
            pulseDao.delete(pulse)
            Result.Success(null)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getAllPulse(): Result<List<Pulse>> {
        return try {
            Result.Success(pulseDao.getAllPulse())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}