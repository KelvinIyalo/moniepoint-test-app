package com.example.moniepointtestapp.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moniepointtestapp.R
import com.example.moniepointtestapp.databinding.ItemLayoutShippingHeaderBinding
import com.example.moniepointtestapp.databinding.ShipmentHistoryItemBinding
import com.example.moniepointtestapp.model.ShipmentDetails

class ShippingAdapter(
    private val context: Context
) : ListAdapter<ShipmentDetails, RecyclerView.ViewHolder>(object :
    DiffUtil.ItemCallback<ShipmentDetails>() {
    override fun areItemsTheSame(
        oldItem: ShipmentDetails,
        newItem: ShipmentDetails
    ): Boolean {
        return oldItem.receipt_number == newItem.receipt_number
    }

    override fun areContentsTheSame(
        oldItem: ShipmentDetails,
        newItem: ShipmentDetails
    ): Boolean {
        return oldItem == newItem
    }
}) {
    override fun submitList(list: List<ShipmentDetails>?) {
        val adapterList: MutableList<ShipmentDetails> = mutableListOf()
        if (list?.isNotEmpty() == true) {
            adapterList.add(ShipmentDetails(isHeader = true, header = "Shipments"))
            adapterList.addAll(list)
        }
        super.submitList(adapterList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null

        when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding =
                    ItemLayoutShippingHeaderBinding.inflate(LayoutInflater.from(parent.context))
                viewHolder = HeaderVH(binding)
            }

            VIEW_TYPE_SHIPPING_ITEM -> {
                val binding = ShipmentHistoryItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                viewHolder = ShippingVH(binding)
            }
        }
        return viewHolder!!
    }

    override fun getItemViewType(position: Int): Int {
        val viewType: Int = if (currentList[position].isHeader) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_SHIPPING_ITEM
        }
        return viewType
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemAtPosition = currentList[position]
        if (itemAtPosition.isHeader) {
            (holder as HeaderVH).bind(itemAtPosition.header)
        } else {
            (holder as ShippingVH).bind(itemAtPosition)
        }
    }


    inner class ShippingVH(
        private val binding: ShipmentHistoryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(transaction: ShipmentDetails) {
            with(binding) {
                statusMsg.text = transaction.status
                amount.text = StringBuilder("$").append(
                    transaction.amount
                )
                when (transaction.status) {

                    "Completed" -> {
                        val drawable = ContextCompat.getDrawable(context, R.drawable.selected_24)
                            ?.apply {
                                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
                            }
                        statusMsg.setCompoundDrawables(drawable, null, null, null)
                        statusMsg.compoundDrawableTintList =
                            ColorStateList.valueOf(context.getColor(R.color.teal_300))

                    }

                    "Pending" -> {
                        statusMsg.setTextColor(context.getColor(R.color.button_color))
                        val drawable = ContextCompat.getDrawable(context, R.drawable.pending_24)
                            ?.apply {
                                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
                            }
                        statusMsg.setCompoundDrawables(drawable, null, null, null)
                    }

                    "In-Progress" -> {
                        statusMsg.setTextColor(context.getColor(R.color.green))
                        val drawable = ContextCompat.getDrawable(context, R.drawable.auto_24)
                            ?.apply {
                                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
                            }
                        statusMsg.setCompoundDrawables(drawable, null, null, null)
                    }


                }

            }
        }
    }

    inner class HeaderVH(private val binding: ItemLayoutShippingHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.title.text = title
        }
    }


    companion object {
        var VIEW_TYPE_SHIPPING_ITEM = 0
        var VIEW_TYPE_HEADER = 1

    }
}