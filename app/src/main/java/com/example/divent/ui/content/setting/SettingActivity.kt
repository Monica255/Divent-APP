package com.example.divent.ui.content.setting

import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.divent.databinding.ActivitySettingBinding
import com.example.divent.util.SettingUtil
import com.example.divent.util.WorkManagerHelper
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySettingBinding
    private val viewModel:SettingsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        binding.timePicker.setIs24HourView(true)

        viewModel.theme.observe(this) { theme ->
            binding.themeSwitch.isChecked = theme == "Dark"
            SettingUtil.setDarkMode(theme == "Dark")
        }

        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            val theme = if (isChecked) "Dark" else "Light"
            viewModel.setTheme(theme)
        }
        val notifSwitchListener = { _: CompoundButton, isChecked: Boolean ->
            viewModel.setNotificationEnabled(isChecked)
            if (isChecked) {
                val hour = binding.timePicker.hour
                val minute = binding.timePicker.minute
                viewModel.setHour(hour)
                viewModel.setMinute(minute)

                WorkManagerHelper.setDailyReminder(this, hour, minute)
                Toast.makeText(
                    this,
                    "Daily reminder is set every ${String.format(Locale.getDefault(), "%02d:%02d", hour, minute)}",
                            Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.setHour(8)
                viewModel.setMinute(0)
                WorkManagerHelper.cancelDailyReminder(this)
            }
        }

        binding.notifSwitch.setOnCheckedChangeListener(notifSwitchListener)

        viewModel.notificationEnabled.observe(this) { isEnabled ->
            binding.notifSwitch.setOnCheckedChangeListener(null) // Temporarily remove the listener
            binding.notifSwitch.isChecked = isEnabled
            binding.notifSwitch.setOnCheckedChangeListener(notifSwitchListener)
        }

        viewModel.time.observe(this) { time ->
            binding.timePicker.hour = time[0]
            binding.timePicker.minute = time[1]
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