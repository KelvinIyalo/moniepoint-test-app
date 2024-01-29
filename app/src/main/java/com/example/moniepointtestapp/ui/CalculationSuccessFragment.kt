package com.example.moniepointtestapp.ui

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.example.moniepointtestapp.R
import com.example.moniepointtestapp.databinding.FragmentCalculationSuccessBinding
import java.text.NumberFormat
import java.util.Locale


class CalculationSuccessFragment : Fragment(R.layout.fragment_calculation_success) {
    lateinit var binding: FragmentCalculationSuccessBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalculationSuccessBinding.bind(view)
        val animation =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        val animationMoveUp = AnimationUtils.loadAnimation(requireContext(), R.anim.move_up)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
        amountCountEffect()
        with(binding) {
            backBtn.setOnClickListener { animateAndNavigation() }
            backBtn.startAnimation(animationMoveUp)
            estimateMessage.startAnimation(animationMoveUp)
            amount.startAnimation(animationMoveUp)
            estimatedAmountLabel.startAnimation(animationMoveUp)
            brandName.startAnimation(animationMoveUp)
        }
    }

    private fun animateAndNavigation() {
        val animationBounce = AnimationUtils.loadAnimation(requireContext(), R.anim.btn_bounce)
        binding.backBtn.startAnimation(animationBounce)
        Handler().postDelayed({
            findNavController().popBackStack(R.id.navigation_home, false)
        }, 400)
    }

    private fun amountCountEffect() {
        // Set initial value to 0
        binding.amount.text = "0"

        // Define the start and end values
        val startValue = 0
        val endValue = 2800 // Set your desired end value

        // Create a ValueAnimator to animate the count-up effect
        val animator = ValueAnimator.ofInt(startValue, endValue)
        animator.duration = 3000 // Set the duration of the animation in milliseconds

        // Update the TextView with the animated value
        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int

            // Format the number with commas (e.g., 1,000)
            val formattedValue =
                NumberFormat.getNumberInstance(Locale.getDefault()).format(animatedValue)

            // Update the TextView text with the formatted value
            binding.amount.text = StringBuilder("$").append(formattedValue)
        }

        // Start the animation
        animator.start()
    }

}