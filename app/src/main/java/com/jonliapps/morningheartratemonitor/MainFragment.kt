package com.jonliapps.morningheartratemonitor


import android.content.SharedPreferences
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
import com.jonliapps.morningheartratemonitor.savepulse.SavePulseViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var binding: FragmentMainBinding

    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        configureBinding(inflater, container)
        configureFab()
        configureButtonStart()
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

    private fun configureButtonStart() {
        binding.btnStart.setOnClickListener {
            if (mainViewModel.state.value != WorkState.RUNNING) {
                mainViewModel.start()
            } else {
                mainViewModel.stop()
            }
        }
    }

    private fun observeWorkState() {
        mainViewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                WorkState.RUNNING -> {
                }
                WorkState.STOPPED -> {
                }
                WorkState.FINISHED -> {
                    showSaveDialog()
                    mainViewModel.stop()
                }
            }
        })
    }

    private fun showSaveDialog() {
        findNavController().navigate(R.id.savePulseFragment)
    }

}
