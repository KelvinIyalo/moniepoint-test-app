package com.example.moniepointtestapp.ui

import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.moniepointtestapp.R
import com.example.moniepointtestapp.adapter.SearchTransactionAdapter
import com.example.moniepointtestapp.adapter.TransactionsAdapter
import com.example.moniepointtestapp.databinding.FragmentSearchBinding
import com.example.moniepointtestapp.model.SearchDeliveries
import com.example.moniepointtestapp.model.ShipmentDetails
import com.example.moniepointtestapp.utils.Utility
import com.example.moniepointtestapp.vm.SearchDeliveriesViewModel
import com.example.moniepointtestapp.vm.TransactionsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchFragment : Fragment(R.layout.fragment_search) {
    lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchDeliveriesViewModel by activityViewModels()
    private lateinit var searchTransactionAdapter: SearchTransactionAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        val animation =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation

        Utility.showNavBar(false, activity)
        initTransactionsRecyclerViewAdapter()
        with(binding) {
            searchEt.requestFocus()
            searchEt.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    val inputMethodManager =
                        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
                }
            }

            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            searchEt.addTextChangedListener {
                viewModel.filterBySearchQuery(it.toString())
            }

            val animation =
                AnimationUtils.loadAnimation(requireContext(), R.anim.move_up)

            // Apply the animation to your TextView
            cardviewRecycler.startAnimation(animation)
        }


        viewModel.transactions.observe(viewLifecycleOwner) { uiState ->
            searchTransactionAdapter.submitList(uiState.searchDeliveries)
            val animation_move =
                AnimationUtils.loadAnimation(requireContext(), R.anim.move_up)

            binding.cardviewRecycler.startAnimation(animation_move)
        }
    }

    private fun initTransactionsRecyclerViewAdapter() {
        searchTransactionAdapter = SearchTransactionAdapter(
            requireContext(),
            onItemClicked = { position: Int, itemAtPosition: SearchDeliveries ->

            }
        )
        binding.searchListRv.adapter = searchTransactionAdapter
    }

}