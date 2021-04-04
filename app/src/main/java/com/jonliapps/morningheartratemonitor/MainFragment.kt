package com.jonliapps.morningheartratemonitor


import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.jonliapps.morningheartratemonitor.databinding.FragmentMainBinding
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber


class MainFragment : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var binding: FragmentMainBinding

    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        configureBinding(inflater, container)
        configureMediaPlayer()
        configureFab()
        configureButtons()
        observeWorkState()

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        return binding.root
    }

    override fun onSharedPreferenceChanged(prefs: SharedPreferences?, key: String?) {
        if (key == "times") {
            mainViewModel.fullTime.value = sharedPreferences.getString("times", "0")?.toLong() ?: 30
        }
    }

    override fun onDetach() {
        super.onDetach()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        mediaPlayer.release()
    }

    private fun configureBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this
    }

    private fun configureFab() {
        binding.fabStatistics.setOnClickListener {
            findNavController().navigate(R.id.statisticsFragment)
        }
    }

    private fun configureButtons() {
        binding.btnStart.setOnClickListener {
            if (mainViewModel.state.value == WorkState.START) {
                mainViewModel.run()
            }
        }
        binding.btnReset.setOnClickListener {
            if (mainViewModel.state.value == WorkState.RUN) {
                mainViewModel.reset()
            }
        }
    }

    private fun observeWorkState() {
        mainViewModel.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                WorkState.START -> {
                    Timber.d("START")
                }
                WorkState.RUN -> {
                    Timber.d("RUN")
                    mediaPlayer.start()
                    binding.motionLayout.transitionToEnd()
                }
                WorkState.FINISH -> {
                    Timber.d("FINISH")
                    mediaPlayer.stop()
                    mainViewModel.start()
                    showSaveDialog()
                    binding.motionLayout.transitionToStart()
                }
                WorkState.RESET -> {
                    Timber.d("RESET")
                    mediaPlayer.stop()
                    mainViewModel.start()
                    binding.motionLayout.transitionToStart()
                }
                else -> {}
            }
        })
    }

    private fun configureMediaPlayer() {
        mediaPlayer = MediaPlayer.create(activity?.applicationContext, R.raw.beep_1)
    }

    private fun showSaveDialog() {
        findNavController().navigate(R.id.savePulseFragment)
    }

}
