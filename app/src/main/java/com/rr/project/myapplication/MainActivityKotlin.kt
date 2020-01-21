package com.rr.project.myapplication

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.rr.project.myapplication.databinding.ActivityMainBinding
import com.rr.project.myapplication.fragment.FragmentCategory
import com.rr.project.myapplication.fragment.FragmentSuperTab
import com.rr.project.myapplication.fragment.FragmentSuperTabKotlin
import com.rr.project.myapplication.utils.Constants

class MainActivityKotlin : AppCompatActivity() {
    private var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        requestPermission()

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_tab -> {
                    openFragment(FragmentSuperTabKotlin())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_categry -> {
                    openFragment(FragmentCategory())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

        openFragment(FragmentSuperTabKotlin())

    }

    private fun openFragment(fragment: androidx.fragment.app.Fragment) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.frame_container, fragment)
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.RECEIVE_SMS
            ),
                    Constants.REQUEST_PERMISSION)
        } else { //Toast.makeText(context, "Permissions already granted", Toast.LENGTH_SHORT).show();
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == Constants.REQUEST_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //Toast.makeText(context, "Permissions granted", Toast.LENGTH_SHORT).show();
        }
    }
}