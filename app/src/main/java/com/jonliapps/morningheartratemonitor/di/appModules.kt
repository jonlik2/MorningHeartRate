package com.jonliapps.morningheartratemonitor.di

import com.jonliapps.morningheartratemonitor.MainViewModel
import com.jonliapps.morningheartratemonitor.statistics.StatisticsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    // ViewModels
    viewModel { MainViewModel() }
    viewModel { StatisticsViewModel() }

}