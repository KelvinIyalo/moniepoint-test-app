package com.example.moniepointtestapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.moniepointtestapp.R
import com.example.moniepointtestapp.databinding.FragmentHomeBinding
import com.example.moniepointtestapp.utils.Utility
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var binding: FragmentHomeBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)




        with(binding) {
            searchEt.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    binding.toolbarSection to "search_toolbar",
                    binding.searchEt to "search_search"
                )

                val direction = R.id.action_navigation_home_to_searchFragment
                findNavController().navigate(
                    direction,
                    null,
                    null,
                    extras
                )
            }

            val animation =
                AnimationUtils.loadAnimation(requireContext(), R.anim.move_up)
//         val navigationView =   requireView().findViewById<BottomNavigationView>(R.id.nav_view)
            navView.menu.findItem(R.id.navigation_home).isChecked = true
            navView.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_calculate -> {
                        val extras = FragmentNavigatorExtras(
                            binding.toolbarSection to "toolbar_calculate",
                            binding.searchEt to "title_calculate"
                        )

                        val direction = R.id.navigation_calculate
                        findNavController().navigate(
                            direction,
                            null,
                            null,
                            extras
                        )
                        true
                    }

                    R.id.navigation_shipment -> {
                        val extras = FragmentNavigatorExtras(
                            binding.toolbarSection to "toolbar_shipment",
                            binding.searchEt to "title_shipment"
                        )

                        val direction = R.id.navigation_shipment
                        findNavController().navigate(
                            direction,
                            null,
                            null,
                            extras
                        )
                        true
                    }
                    // Add cases for other BottomNavigationView items if needed
                    else -> false
                }
            }
            // Apply the animation to your TextView
            binding.availableVehicles.startAnimation(animation)
            binding.shipmentDetails.startAnimation(animation)
            binding.trackingLabel.startAnimation(animation)
        }
    }

    override fun onResume() {
        super.onResume()
        Utility.showNavBar(true, activity)
        binding.navView.menu.findItem(R.id.navigation_home).isChecked = true
    }

}