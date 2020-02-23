package com.jonliapps.morningheartratemonitor


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jonliapps.morningheartratemonitor.databinding.FragmentMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        configureBinding(inflater, container)
        configureFab()
        configureButtonStart()
        observeWorkState()

        return binding.root
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
            if (mainViewModel.state.value != WorkState.STARTED) {
                mainViewModel.start()
            } else {
                mainViewModel.stop()
            }
        }
    }

    private fun observeWorkState() {
        mainViewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                WorkState.STARTED -> {
                }
                WorkState.STOP -> {
                }
                WorkState.FINISH -> {
                    showALert()
                }
            }
        })
    }

    private fun showALert() {
        val builder = AlertDialog.Builder(context!!)
        builder.apply {
            setTitle("Сохранить")
            setMessage("Сохранить результат?")
            setPositiveButton("Сохранить") { _, _ ->
                val randomPulse: Int = (30..100).random()
                mainViewModel.save(randomPulse)
            }
            setNegativeButton("Отмена") { _, _ ->

            }
            val dialog = builder.create()
            dialog.show()
        }
    }
}
