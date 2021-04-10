package com.jonliapps.morningheartratemonitor

import android.app.Application
import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainViewModel(application: Application) : ViewModel() {

    companion object {
        const val TIME_INTERVAL = 100L
    }

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
    var fullTime: MutableLiveData<Long> = MutableLiveData(sharedPreferences.getString("times", "0")?.toLong() ?: 30)

    private var timer: CountDownTimer
    private var secondsLeft = 0

    val currentTimeString = Transformations.map(fullTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    private val _state = MutableLiveData(WorkState.START)
    val state: LiveData<WorkState> = _state

    private lateinit var job: Job

    init {
        timer = object : CountDownTimer((fullTime.value ?: 30) * 1000L, TIME_INTERVAL) {
            override fun onFinish() {
                fullTime.value = sharedPreferences.getString("times", "0")?.toLong() ?: 30
                _state.value = WorkState.FINISH
            }

            override fun onTick(p0: Long) {
                if ((p0.toFloat() / 1000.0f).roundToInt() != secondsLeft) {
                    secondsLeft = (p0.toFloat() / 1000.0f).roundToInt()
                    fullTime.value = secondsLeft.toLong()
                }
            }
        }
    }

    fun start() {
        _state.value = WorkState.START
        fullTime.value = sharedPreferences.getString("times", "0")?.toLong() ?: 30
    }

    fun run() {
        _state.value = WorkState.RUN
        initTimer()
    }

    fun reset() {
        _state.value = WorkState.RESET
        resetTimer()
    }

    private fun initTimer() {
        job = Job()
        viewModelScope.launch(job) {
            delay(3000)
            timer.start()
        }
    }

    private fun resetTimer() {
        timer.cancel()
        job.cancel()
    }

}