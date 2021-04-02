package com.ben.taskplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.fragment) }
    private val bottomNavigationView: BottomNavigationView by lazy { findViewById(R.id.bottomNavView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavBarConfig()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun bottomNavBarConfig() {
        bottomNavigationView.background = null
        bottomNavigationView.itemRippleColor = null

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.taskMenuFragment, R.id.quickNoteFragment, R.id.profileFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
    }
}