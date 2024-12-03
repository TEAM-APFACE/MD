package com.teamapface.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.teamapface.databinding.FragmentSettingsBinding
import com.teamapface.utils.ThemeUtils

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        // Initialize the switch state
        val isDarkMode = ThemeUtils.isDarkMode(requireContext())
        binding.themeSwitch.isChecked = isDarkMode

        // Handle switch toggle
        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            ThemeUtils.saveThemePreference(requireContext(), isChecked)
            ThemeUtils.applyTheme(requireContext())
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
