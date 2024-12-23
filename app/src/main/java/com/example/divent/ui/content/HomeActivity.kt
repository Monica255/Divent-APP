package com.example.divent.ui.content

import android.os.Bundle
import android.view.MenuInflater
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.divent.R
import com.example.divent.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSmoothBottomMenu()

    }

    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_bottom_navbar, popupMenu.menu)

        val navController: NavController = findNavController(R.id.frame_container)
        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)
    }

}