package com.example.moniepointtestapp.utils

import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.example.moniepointtestapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

object Utility {

    fun showNavBar(isShowNavbar: Boolean, fragment: FragmentActivity?) {
        val bottomSheetContainer = fragment?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomSheetContainer?.isVisible = isShowNavbar
    }

}