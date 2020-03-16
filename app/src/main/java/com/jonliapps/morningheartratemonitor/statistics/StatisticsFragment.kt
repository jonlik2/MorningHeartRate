package com.jonliapps.morningheartratemonitor.statistics


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.jonliapps.morningheartratemonitor.R
import com.jonliapps.morningheartratemonitor.databinding.FragmentStatisticsBinding
import com.jonliapps.morningheartratemonitor.db.Pulse
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.viewModel


class StatisticsFragment : Fragment() {

    private lateinit var binding: FragmentStatisticsBinding

    private val statisticsViewModel: StatisticsViewModel by viewModel()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StatisticsAdapter

    private lateinit var chart: LineChart

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
        recyclerView.adapter = adapter
    }

    private fun setupObserverViewModel() {
        statisticsViewModel.pulses.observe(viewLifecycleOwner, Observer {
            updatePulsesList(it)
        })
    }

    private fun updatePulsesList(pulses: List<Pulse>) {
        adapter.pulses = pulses
        setupChartPulse(pulses)
        adapter.notifyDataSetChanged()
    }

    private fun setupChartPulse(pulses: List<Pulse>) {
        chart = binding.chartPulse

        val entries = mutableListOf<Entry>()
        pulses.reversed().forEachIndexed { index, pulse ->
            entries.add(Entry(index.toFloat(), pulse.value.toFloat()))
        }

        val dataSet = LineDataSet(entries.toList(), "")

        val lineData = LineData(dataSet)
        chart.data = lineData
        chart.setNoDataText("Нет данных")
        chart.description.isEnabled = false
        chart.isScaleYEnabled = false

        val xAxis = chart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        chart.axisRight.isEnabled = false
        val leftAxis = chart.axisLeft
        leftAxis.setDrawGridLines(false)

        chart.invalidate()
    }



}
