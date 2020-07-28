package com.jonliapps.morningheartratemonitor.savepulse

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.jonliapps.morningheartratemonitor.EventObserver
import com.jonliapps.morningheartratemonitor.R
import com.jonliapps.morningheartratemonitor.databinding.DialogSavePulseBinding
import org.koin.android.viewmodel.ext.android.viewModel

class SavePulseFragment : DialogFragment() {

    private lateinit var binding: DialogSavePulseBinding

    private val savePulseViewModel: SavePulseViewModel by viewModel()

    private lateinit var toolbarDialog: Toolbar
    private lateinit var editTextPulse: EditText

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NORMAL,
            R.style.AppTheme_FullScreenDialog
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        configureBinding(inflater, container)

        toolbarDialog = binding.toolbarDialog
        editTextPulse = binding.etPulse

        setupObserverViewModel()
        setupTextToLabel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarDialog.setNavigationOnClickListener { dismiss() }
        toolbarDialog.inflateMenu(R.menu.dialog_menu)
        toolbarDialog.setOnMenuItemClickListener {
            savePulseViewModel.save(editTextPulse.text.toString().toInt())
            return@setOnMenuItemClickListener true
        }
    }

    private fun configureBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_save_pulse, container, false)
        binding.viewModel = savePulseViewModel
        binding.lifecycleOwner = this
    }

    private fun setupObserverViewModel() {
        savePulseViewModel.openMainFragment.observe(viewLifecycleOwner, EventObserver {
            openMainFragment()
        })
    }

    private fun openMainFragment() {
        findNavController().navigate(R.id.mainFragment)
    }

    private fun setupTextToLabel() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext)
        val pulseValue = sharedPreferences.getString("times", "0")?.toInt()
        pulseValue?.let {
            savePulseViewModel.generateTextToLabel(it)
        }
    }

}