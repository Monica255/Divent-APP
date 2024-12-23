package com.example.divent.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.divent.databinding.ActivitySplashBinding
import com.example.divent.ui.content.HomeActivity
import com.example.divent.ui.content.setting.SettingsViewModel
import com.example.divent.util.SettingUtil
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val viewModel:SettingsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.theme.observe(this) { theme ->
            val dark =theme == "Dark"
            SettingUtil.SetDarkMode(dark)
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }, DELAY_TIME)
        }


    }
    companion object {
        const val DELAY_TIME: Long = 2_000
    }
}