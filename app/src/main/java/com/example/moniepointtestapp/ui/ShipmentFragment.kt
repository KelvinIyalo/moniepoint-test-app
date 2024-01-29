package com.example.moniepointtestapp.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.moniepointtestapp.R
import com.example.moniepointtestapp.adapter.ShippingAdapter
import com.example.moniepointtestapp.databinding.FragmentShipmentBinding
import com.example.moniepointtestapp.model.ShipmentStatus
import com.example.moniepointtestapp.model.TableItem
import com.example.moniepointtestapp.utils.UiState
import com.example.moniepointtestapp.utils.Utility
import com.example.moniepointtestapp.utils.Utility.startMoveUpAnimation
import com.example.moniepointtestapp.vm.ShipmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShipmentFragment : Fragment(R.layout.fragment_shipment) {
    lateinit var binding: FragmentShipmentBinding
    private val viewModel: ShipmentViewModel by activityViewModels()
    private lateinit var transactionsAdapter: ShippingAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentShipmentBinding.bind(view)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
        with(binding) {
            backBtn.setOnClickListener { findNavController().popBackStack() }
        }

        initTransactionsRecyclerViewAdapter()
        initClickListeners()

        viewModel.transactions.observe(viewLifecycleOwner) { uiState ->
            transactionsAdapter.submitList(uiState.shipmentDetails)
            binding.shipmentRecyclerview.startMoveUpAnimation(requireContext())
            updateSelectedTab(uiState.shipmentStatus!!)
        }
    }

    private fun updateSelectedTab(
        shipmentStatus: ShipmentStatus
    ) {
        val barSelectedColor = R.color.button_color
        val barUnselectedColor = R.color.purple_700
        val countUnselectedColor = resources.getColor(R.color.gray)
        val selectedTextColor = resources.getColor(R.color.white)

        val table = mapOf(
            ShipmentStatus.ALL to TableItem(binding.allLiner, binding.all, binding.allCount),
            ShipmentStatus.COMPLETED to TableItem(
                binding.completedLiner,
                binding.completed,
                binding.completedCount
            ),
            ShipmentStatus.IN_PROGRESS to TableItem(
                binding.inProgressLiner,
                binding.inProgress,
                binding.inProgressCount
            ),
            ShipmentStatus.CANCELLED to TableItem(
                binding.canceledLiner,
                binding.canceled,
                binding.canceledCount
            ),
            ShipmentStatus.PENDING_ORDER to TableItem(
                binding.pendingLiner,
                binding.pending,
                binding.pendingCount
            ),
        )


        table.forEach { (status, tableItem) ->
            tableItem.bar.setBackgroundResource(if (shipmentStatus == status) barSelectedColor else barUnselectedColor)
            tableItem.count.backgroundTintList =
                ColorStateList.valueOf(if (shipmentStatus == status) resources.getColor(R.color.button_color) else countUnselectedColor)
            tableItem.text.setTextColor(if (shipmentStatus == status) selectedTextColor else countUnselectedColor)
        }

    }

    private fun initTransactionsRecyclerViewAdapter() {
        transactionsAdapter = ShippingAdapter(
            requireContext()
        )
        binding.shipmentRecyclerview.adapter = transactionsAdapter

    }

    private fun initClickListeners() {
        viewModel.retrieveAllShippingCount().observe(requireActivity(), Observer { response ->
            when (response) {

                is UiState.Success -> {

                    val statusCounts = mapOf(
                        "All" to response.data!!.size,
                        "Completed" to response.data.count { it.status.contentEquals("Completed") },
                        "Pending" to response.data.count { it.status.contentEquals("Pending") },
                        "In-Progress" to response.data.count { it.status.contentEquals("In-Progress") },
                        "Canceled" to response.data.count { it.status.contentEquals("Canceled") }
                    )


                    with(binding) {

                        val textViewMap = mapOf(
                            "All" to allCount,
                            "Completed" to completedCount,
                            "Pending" to pendingCount,
                            "In-Progress" to inProgressCount,
                            "Canceled" to canceledCount
                        )
                        statusCounts.forEach { (status, count) ->
                            textViewMap[status]?.apply {
                                isVisible = count > 0
                                text = count.toString()
                            }
                        }

                    }


                }

                else -> {}
            }
        })


        with(binding) {
            val filterButtons = listOf(allLayout, completedLayout, pendingLayout, canceledLayout, inProgressLayout)
            val statuses = listOf(ShipmentStatus.ALL, ShipmentStatus.COMPLETED, ShipmentStatus.PENDING_ORDER, ShipmentStatus.CANCELLED, ShipmentStatus.IN_PROGRESS)

            filterButtons.forEachIndexed { index, layout ->
                layout.setOnClickListener { viewModel.filterByStatus(statuses[index]) }
            }
        }


    }

    override fun onResume() {
        super.onResume()
    }
}

