package com.jonliapps.morningheartratemonitor

import android.app.Application
import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainViewModel(private val application: Application) : ViewModel() {

    companion object {
        const val TIME_INTERVAL = 100L
    }

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
    var fullTime: MutableLiveData<Long> = MutableLiveData(sharedPreferences.getString("times", "0")?.toLong() ?: 30)

    private lateinit var timer: CountDownTimer
    private var secondsLeft = 0

    val currentTimeString = Transformations.map(fullTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    val state = MutableLiveData<WorkState>(WorkState.STOPPED)

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
            timer = object : CountDownTimer(fullTime.value!! * 1000L, TIME_INTERVAL) {
                override fun onFinish() {
                    fullTime.value = sharedPreferences.getString("times", "0")?.toLong() ?: 30
                    state.value = WorkState.FINISHED
                }

                override fun onTick(p0: Long) {
                    if ((p0.toFloat() / 1000.0f).roundToInt() != secondsLeft) {
                        secondsLeft = (p0.toFloat() / 1000.0f).roundToInt()
                        fullTime.value = secondsLeft.toLong()
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