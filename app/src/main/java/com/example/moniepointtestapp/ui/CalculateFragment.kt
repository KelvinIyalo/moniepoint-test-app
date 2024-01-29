package com.example.moniepointtestapp.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.transition.TransitionInflater
import android.view.View
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.compose.animation.core.Animation
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.moniepointtestapp.R
import com.example.moniepointtestapp.adapter.CategoriesAdapter
import com.example.moniepointtestapp.databinding.CategoryTypeBinding
import com.example.moniepointtestapp.databinding.FragmentCalculateBinding
import com.example.moniepointtestapp.model.Categories
import com.example.moniepointtestapp.utils.Utility
import com.example.moniepointtestapp.utils.Utility.startMoveUpAnimation
import com.example.moniepointtestapp.vm.CategoriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class CalculateFragment : Fragment(R.layout.fragment_calculate) {
    lateinit var binding: FragmentCalculateBinding
    private val viewModel: CategoriesViewModel by activityViewModels()
    private var isSelected: Boolean = false
    private lateinit var categoriesAdapter: CategoriesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalculateBinding.bind(view)
        val animation =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation

        with(binding) {
            calculateBtn.setOnClickListener {
                animateAndNavigation()
            }
            backBtn.setOnClickListener { findNavController().popBackStack() }
            calculateBtn.startMoveUpAnimation(requireContext())
            categoriesLabel.startMoveUpAnimation(requireContext())
            quest2Label.startMoveUpAnimation(requireContext())
            questLabel.startMoveUpAnimation(requireContext())
            packagingLabel.startMoveUpAnimation(requireContext())
            sendingPackageCard.startMoveUpAnimation(requireContext())
            destinationLabel.startMoveUpAnimation(requireContext())
            cardParams.startMoveUpAnimation(requireContext())
        }
        initTransactionsRecyclerViewAdapter()

        viewModel.transactions.observe(viewLifecycleOwner) { uiState ->
            categoriesAdapter.submitList(uiState.categories)
        }
    }

    private fun animateAndNavigation() {
        val animationBounce = AnimationUtils.loadAnimation(requireContext(), R.anim.btn_bounce)
        binding.calculateBtn.startAnimation(animationBounce)
        Handler().postDelayed({
            val direction = R.id.action_navigation_calculate_to_calculationSuccessFragment
            val extras = FragmentNavigatorExtras(
                binding.boxIc to "calculate_success"
            )
            findNavController().navigate(direction, null, null, extras)
        }, 400)

    }

    private fun initTransactionsRecyclerViewAdapter() {
        categoriesAdapter = CategoriesAdapter(
            onItemClicked = { position: Int, itemAtPosition: Categories, itemView ->
                isSelected = !isSelected
                if (isSelected) {
                    setViewManagement(itemView)
                } else {
                    resetViewManagement(itemView)
                }
            }
        )
        binding.categoriesRv.adapter = categoriesAdapter
    }

    private fun setViewManagement(itemView: CategoryTypeBinding) {
        with(itemView) {

            categoryLayout.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.black))
            itemName.setTextColor(resources.getColor(R.color.white))
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.selected_24)
                ?.apply {
                    setBounds(0, 0, intrinsicWidth, intrinsicHeight)
                }
            itemName.setCompoundDrawables(drawable, null, null, null)

        }

    }

    private fun resetViewManagement(itemView: CategoryTypeBinding) {
        with(itemView) {
            categoryLayout.backgroundTintList = null
            categoryLayout.setBackgroundResource(R.drawable.categories_widget)
            itemName.setTextColor(resources.getColor(R.color.black))
            itemName.setCompoundDrawables(null, null, null, null)
        }

    }

}