package com.jonliapps.morningheartratemonitor.savepulse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.jonliapps.morningheartratemonitor.EventObserver
import com.jonliapps.morningheartratemonitor.R
import org.koin.android.viewmodel.ext.android.viewModel

class SavePulseFragment : DialogFragment() {

    private val savePulseViewModel: SavePulseViewModel by viewModel()

    private lateinit var toolbarDialog: Toolbar
    private lateinit var editTextPulse: EditText

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
        val view = inflater.inflate(R.layout.dialog_save_pulse, container, false)

        toolbarDialog = view.findViewById(R.id.toolbar_dialog)
        editTextPulse = view.findViewById(R.id.et_pulse)

        setupObserverViewModel()

        return view
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

    private fun setupObserverViewModel() {
        savePulseViewModel.openMainFragment.observe(viewLifecycleOwner, EventObserver {
            openMainFragment()
        })
    }

    private fun openMainFragment() {
        findNavController().navigate(R.id.mainFragment)
    }

}