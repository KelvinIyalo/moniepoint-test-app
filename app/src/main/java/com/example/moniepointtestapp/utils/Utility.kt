package com.example.moniepointtestapp.utils

import android.content.Context
import android.transition.TransitionInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.example.moniepointtestapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

object Utility {
    fun View.startMoveUpAnimation(context: Context) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.move_up)
        startAnimation(animation)
    }

}