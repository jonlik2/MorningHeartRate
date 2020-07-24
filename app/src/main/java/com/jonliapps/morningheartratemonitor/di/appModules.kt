package com.jonliapps.morningheartratemonitor.di

import androidx.room.Room
import com.jonliapps.morningheartratemonitor.MainViewModel
import com.jonliapps.morningheartratemonitor.db.PulseDatabase
import com.jonliapps.morningheartratemonitor.db.PulseDatasource
import com.jonliapps.morningheartratemonitor.db.PulseRepository
import com.jonliapps.morningheartratemonitor.savepulse.SavePulseViewModel
import com.jonliapps.morningheartratemonitor.settings.SettingsViewModel
import com.jonliapps.morningheartratemonitor.statistics.StatisticsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            PulseDatabase::class.java,
            "pulse_db.db"
        ).build()
    }

    single { PulseDatasource(pulseDao = get<PulseDatabase>().pulseDao()) }

    single { PulseRepository(pulseDatasource = get()) }

    // ViewModels
    viewModel { MainViewModel(application = androidApplication()) }
    viewModel { StatisticsViewModel(pulseRepository = get()) }
    viewModel { SavePulseViewModel(pulseRepository = get()) }
    viewModel { SettingsViewModel() }

}