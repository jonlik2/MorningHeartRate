package com.jonliapps.morningheartratemonitor.savepulse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonliapps.morningheartratemonitor.Event
import com.jonliapps.morningheartratemonitor.db.Pulse
import com.jonliapps.morningheartratemonitor.db.PulseRepository
import com.jonliapps.morningheartratemonitor.utils.Result
import com.jonliapps.morningheartratemonitor.utils.idGenerated
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class SavePulseViewModel(val pulseRepository: PulseRepository) : ViewModel() {

    private val _openMainFragment = MutableLiveData<Event<Unit>>()
    val openMainFragment: LiveData<Event<Unit>> = _openMainFragment

    private val _textToLabel = MutableLiveData<String>()
    val textToLabel: LiveData<String> = _textToLabel

    fun save(result: Int) {
        viewModelScope.launch {
            val pulse = Pulse(idGenerated, result, Calendar.getInstance().time)
            when (pulseRepository.save(pulse)) {
                is Result.Success -> {
                    Timber.d("${pulse.value} in ${pulse.date}")
                    _openMainFragment.value = Event(Unit)
                }
                is Result.Error -> Timber.d("error saved")
            }
        }
    }

    fun generateTextToLabel(pulseValue: Int) {
        val count = 60 / pulseValue
        if (pulseValue != 60) {
            _textToLabel.value =
                "Умножьте на $count количество ударов за $pulseValue секунд и введите полученное значение"
        } else {
            _textToLabel.value =
                "Введите полученное значение"
        }
    }

}