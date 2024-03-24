package com.practicum.playlistmaker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class RootActivity : AppCompatActivity(R.layout.activity_root) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        val buttomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_view)
        buttomNavigationView.setupWithNavController(navController)
    }
}
