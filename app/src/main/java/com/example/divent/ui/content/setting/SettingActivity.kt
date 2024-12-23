package com.example.divent.ui.content.setting

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.divent.R
import com.example.divent.databinding.ActivitySettingBinding
import com.example.divent.ui.content.SettingsDataStore
import com.example.divent.util.SettingUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySettingBinding
    private lateinit var settingsDataStore: SettingsDataStore
    private val viewModel:SettingsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        viewModel.theme.observe(this) { theme ->
            binding.themeSwitch.isChecked = theme == "Dark"
            SettingUtil.SetDarkMode(theme == "Dark")
        }

        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            val theme = if (isChecked) "Dark" else "Light"
            viewModel.setTheme(theme)
        }

        viewModel.notificationEnabled.observe(this) { isEnabled ->
            binding.notifSwitch.isChecked = isEnabled
        }

        binding.notifSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setNotificationEnabled(isChecked)
//            if (isChecked) {
//                showNotification()
//            } else {
//                cancelNotification()
//            }
        }
    }



    private fun setActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}