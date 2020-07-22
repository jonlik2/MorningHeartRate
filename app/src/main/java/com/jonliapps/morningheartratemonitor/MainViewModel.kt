package com.jonliapps.morningheartratemonitor

import android.os.CountDownTimer
import android.os.SystemClock
import android.text.format.DateUtils
import androidx.lifecycle.*
import com.jonliapps.morningheartratemonitor.db.PulseRepository
import kotlinx.coroutines.launch
import kotlin.math.round
import kotlin.math.roundToInt

class MainViewModel(val pulseRepository: PulseRepository) : ViewModel() {

    companion object {
        const val ZERO = 0L
        const val TIME_INTERVAL = 100L
        const val END_TIME = 10000L
    }

    val state = MutableLiveData<WorkState>(WorkState.STOPPED)

    private lateinit var timer: CountDownTimer
    private var secondsLeft = 0

    private val _currentTime = MutableLiveData<Long>(END_TIME / 1000)
    val currentTime: LiveData<Long> = _currentTime

    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    fun start() {
        state.value = WorkState.RUNNING
        initTimer()
    }

    fun stop() {
        state.value = WorkState.STOPPED
        resetTimer()
    }

    private fun initTimer() {
        viewModelScope.launch {
            timer = object : CountDownTimer(END_TIME, TIME_INTERVAL) {
                override fun onFinish() {
                    _currentTime.value = END_TIME / 1000
                    state.value = WorkState.FINISHED
                }

                override fun onTick(p0: Long) {
                    if ((p0.toFloat() / 1000.0f).roundToInt() != secondsLeft) {
                        secondsLeft = (p0.toFloat() / 1000.0f).roundToInt()
                        _currentTime.value = secondsLeft.toLong()
                    }
                }
            }
            timer.start()
        }
    }

    private fun resetTimer() {
        timer.cancel()
    }

}