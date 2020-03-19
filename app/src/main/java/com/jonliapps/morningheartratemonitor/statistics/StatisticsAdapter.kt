package com.jonliapps.morningheartratemonitor.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jonliapps.morningheartratemonitor.databinding.RvPulseItemBinding
import com.jonliapps.morningheartratemonitor.db.Pulse

class StatisticsAdapter(
    var pulses: List<Pulse>,
    private val viewModel: StatisticsViewModel
) : RecyclerView.Adapter<StatisticsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsViewHolder {
        return StatisticsViewHolder.from(parent)
    }

    override fun getItemCount(): Int = pulses.size

    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) {
        holder.bind(viewModel, pulses[position])
    }

    fun removeAt(position: Int) {
        viewModel.delete(pulses[position])
        notifyItemRemoved(position)
    }

}

class StatisticsViewHolder(private val binding: RvPulseItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(viewModel: StatisticsViewModel, pulse: Pulse) {
        binding.pulse = pulse
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): StatisticsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RvPulseItemBinding.inflate(layoutInflater, parent, false)
            return StatisticsViewHolder(binding)
        }
    }
}