package com.jonliapps.morningheartratemonitor.statistics

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonliapps.morningheartratemonitor.db.Pulse
import com.jonliapps.morningheartratemonitor.db.PulseRepository
import com.jonliapps.morningheartratemonitor.utils.Result
import kotlinx.coroutines.launch
import timber.log.Timber

class StatisticsViewModel(val pulseRepository: PulseRepository) : ViewModel() {

    private val _pulses = MutableLiveData<List<Pulse>>()
    val pulses: LiveData<List<Pulse>> = _pulses

    init {
        fetchPulsesList()
    }

    private fun fetchPulsesList() {
        viewModelScope.launch {
            when (val result = pulseRepository.getAllPulse()) {
                is Result.Success -> _pulses.value = result.data
                is Result.Error -> Timber.tag("III").d("error fetch pulses")
            }
        }
    }

    fun delete(pulse: Pulse) {
        viewModelScope.launch {
            when (pulseRepository.delete(pulse)) {
                is Result.Success -> {
                    fetchPulsesList()
                    Timber.tag("III").d("delete ${pulse.value}")
                }
                is Result.Error -> Timber.tag("III").d("error delete")
            }
        }
    }
}