package com.sundaydavid989.shesmakeup

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sundaydavid989.shesmakeup.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //navController setup
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.toolbar, navController)
        setDestinationListener()
    }

    private fun setDestinationListener(){
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.homeFragment,
                R.id.searchFragment,
                R.id.itemDetailFragment -> {
                    hideToolBar()
                }else -> {
                    showToolBar()
            }
            }
        }
    }

    override fun onBackPressed() {
       if (navController.currentDestination!!.id == R.id.homeFragment){
        showDialog()
       }else{
           super.onBackPressed()
       }
    }

    private fun showDialog() {
        val dialog = MaterialAlertDialogBuilder(this@MainActivity)
        dialog.setTitle("Exiting?")
        dialog.setIcon(R.drawable.ic_ion_close_circle_sharp)
        dialog.setMessage("Are you sure you want to exit?")
            .setPositiveButton(
                "YES"
            ) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
                exitProcess(0)
            }
            .setNegativeButton(
                "NO"
            ) { dialogInterface: DialogInterface, _: Int -> dialogInterface.dismiss() }
        dialog.create().show()
    }
    private fun showToolBar(){
        binding.toolbar.visibility = View.VISIBLE
    }
    private fun hideToolBar(){
        binding.toolbar.visibility = View.GONE
    }
}