package com.jonliapps.morningheartratemonitor.statistics


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.jonliapps.morningheartratemonitor.R
import com.jonliapps.morningheartratemonitor.databinding.FragmentStatisticsBinding
import com.jonliapps.morningheartratemonitor.db.Pulse
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class StatisticsFragment : Fragment() {

    private lateinit var binding: FragmentStatisticsBinding

    private val statisticsViewModel: StatisticsViewModel by viewModel()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StatisticsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        configureBinding(inflater, container)
        configureRecyclerView()
        setupObserverViewModel()

        return binding.root
    }

    private fun configureBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false)
        binding.viewModel = statisticsViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
    }

    private fun configureRecyclerView() {
        recyclerView = binding.rvPulse
        adapter = StatisticsAdapter(listOf(), statisticsViewModel)
        //recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    private fun setupObserverViewModel() {
        statisticsViewModel.pulses.observe(viewLifecycleOwner, Observer {
            updatePulsesList(it)
        })
    }

    private fun updatePulsesList(pulses: List<Pulse>) {
        adapter.pulses = pulses
        adapter.notifyDataSetChanged()
    }

}
