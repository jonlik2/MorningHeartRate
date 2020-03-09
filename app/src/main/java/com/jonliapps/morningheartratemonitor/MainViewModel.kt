package com.jonliapps.morningheartratemonitor

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.*
import com.jonliapps.morningheartratemonitor.db.Pulse
import com.jonliapps.morningheartratemonitor.db.PulseRepository
import com.jonliapps.morningheartratemonitor.utils.Result
import com.jonliapps.morningheartratemonitor.utils.idGenerated
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(val pulseRepository: PulseRepository) : ViewModel() {

    companion object {
        const val ZERO = 0L
        const val ONE_SECOND = 1000L
        const val END_TIME = 10000L
    }

    val state = MutableLiveData<WorkState>(WorkState.STOP)

    private val timer: CountDownTimer

    private val _currentTime = MutableLiveData<Long>(END_TIME / 1000)
    val currentTime: LiveData<Long> = _currentTime

    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    init {
        timer = object : CountDownTimer(END_TIME, ONE_SECOND) {
            override fun onFinish() {
                _currentTime.value = END_TIME / 1000
                state.value = WorkState.FINISH
            }

            override fun onTick(p0: Long) {
                _currentTime.value = p0 / ONE_SECOND
            }
        }
    }

    fun start() {
        state.value = WorkState.STARTED
        timer.start()
    }

    fun stop() {
        state.value = WorkState.STOP
        timer.cancel()
    }

}