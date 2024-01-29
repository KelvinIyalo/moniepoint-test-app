package com.example.moniepointtestapp.adapter

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moniepointtestapp.R
import com.example.moniepointtestapp.databinding.ShipmentHistoryItemBinding
import com.example.moniepointtestapp.model.ShipmentDetails


class TransactionsAdapter(
    private val context: Context,
    private val onItemClicked: (position: Int, itemAtPosition: ShipmentDetails) -> Unit
) : ListAdapter<ShipmentDetails, TransactionsAdapter.TransactionHistoryVH>(object :
    DiffUtil.ItemCallback<ShipmentDetails>() {

    override fun areItemsTheSame(oldItem: ShipmentDetails, newItem: ShipmentDetails): Boolean {
        return oldItem.status == newItem.status
    }

    override fun areContentsTheSame(oldItem: ShipmentDetails, newItem: ShipmentDetails): Boolean {
        return oldItem == newItem
    }

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHistoryVH {
        //inflate the view to be used by the payment option view holder
        val binding =
            ShipmentHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionHistoryVH(binding, onItemClick = { position ->
            val itemAtPosition = currentList[position]
            this.onItemClicked(position, itemAtPosition)
        })


    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: TransactionHistoryVH, position: Int) {
        val itemAtPosition = currentList[position]
        holder.bind(itemAtPosition)
    }


    inner class TransactionHistoryVH(
        private val binding: ShipmentHistoryItemBinding,
        onItemClick: (position: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

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


                }

            }
        }

    }

}