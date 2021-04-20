package com.ben.taskplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ben.taskplanner.view.SharedTokenViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
    }
    private val navController by lazy { navHostFragment.findNavController() }
    private val bottomNavigationView: BottomNavigationView by lazy { findViewById(R.id.bottomNavView) }
    private val sharedTokenViewModel: SharedTokenViewModel by viewModels()
    private val customToolbar: Toolbar by lazy { findViewById(R.id.custom_toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(customToolbar)
        bottomNavBarConfig()
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        sharedTokenViewModel.readAccessToken().observe(this) { token ->
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
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.homeFragment,
                R.id.taskMenuFragment,
                R.id.quickNoteFragment,
                R.id.profileFragment,
                R.id.credentialsFragment
            )
        )
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
                R.id.forgotPasswordFragment -> {
                    hideBottomBar()
                }
                R.id.verificationCodeFragment -> {
                    hideBottomBar()
                }
                R.id.resetPasswordFragment -> {
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