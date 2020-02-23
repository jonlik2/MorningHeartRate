package com.jonliapps.morningheartratemonitor.db

import com.jonliapps.morningheartratemonitor.utils.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class PulseRepository(private val pulseDatasource: PulseDatasource) {

    suspend fun save(pulse: Pulse): Result<Void?> = coroutineScope {
        val task = async { pulseDatasource.save(pulse) }
        return@coroutineScope task.await()
    }

    suspend fun getAllPulse(): Result<List<Pulse>> = coroutineScope {
        val task = async { pulseDatasource.getAllPulse() }
        return@coroutineScope task.await()
    }

}