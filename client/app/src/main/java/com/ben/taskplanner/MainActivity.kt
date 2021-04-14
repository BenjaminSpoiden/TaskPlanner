package com.ben.taskplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ben.taskplanner.util.TaskPlannerDataStore
import com.ben.taskplanner.view.SharedTokenViewModel
import com.ben.taskplanner.view.my_task.MyTaskViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.fragment) }
    private val bottomNavigationView: BottomNavigationView by lazy { findViewById(R.id.bottomNavView) }
    private val sharedTokenViewModel: SharedTokenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavBarConfig()
        supportActionBar?.elevation = 0F

        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        sharedTokenViewModel.readAccessToken().observe(this) { token ->
            Log.d("Tag", "Token: $token")
            if(!token.isNullOrEmpty()) {
                navGraph.startDestination = R.id.homeFragment
                navController.graph = navGraph
            }else {
                navGraph.startDestination = R.id.credentialsFragment
                navController.graph = navGraph
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun bottomNavBarConfig() {
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.homeFragment,
            R.id.taskMenuFragment,
            R.id.quickNoteFragment,
            R.id.profileFragment,
            R.id.credentialsFragment
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.loginFragment -> {
                    hideBottomBar()
                }
                R.id.credentialsFragment -> {
                    hideBottomBar()
                }
                R.id.registerFragment -> {
                    hideBottomBar()
                }
                else -> {
                    showBottomBar()
                }
            }
        }
    }

    private fun hideBottomBar() {
        bottomNavigationView.visibility = View.GONE
    }

    private fun showBottomBar() {
        bottomNavigationView.visibility = View.VISIBLE
    }
}