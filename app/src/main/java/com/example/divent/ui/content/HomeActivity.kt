package com.example.divent.ui.content

import android.content.Intent
import android.os.Bundle
import android.view.MenuInflater
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.divent.R
import com.example.divent.databinding.ActivityHomeBinding
import com.example.divent.ui.content.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSmoothBottomMenu()

        binding.setting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_bottom_navbar, popupMenu.menu)

        val navController: NavController = findNavController(R.id.frame_container)
        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)
    }
}