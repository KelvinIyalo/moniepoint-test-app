package com.example.moniepointtestapp.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moniepointtestapp.R
import com.example.moniepointtestapp.databinding.SearchItemsBinding
import com.example.moniepointtestapp.databinding.ShipmentHistoryItemBinding
import com.example.moniepointtestapp.model.SearchDeliveries
import com.example.moniepointtestapp.model.ShipmentDetails


class SearchTransactionAdapter(
    private val context: Context,
    private val onItemClicked: (position: Int, itemAtPosition: SearchDeliveries) -> Unit
) : ListAdapter<SearchDeliveries, SearchTransactionAdapter.TransactionHistoryVH>(object :
    DiffUtil.ItemCallback<SearchDeliveries>() {

    override fun areItemsTheSame(oldItem: SearchDeliveries, newItem: SearchDeliveries): Boolean {
        return oldItem.status == newItem.status
    }

    override fun areContentsTheSame(oldItem: SearchDeliveries, newItem: SearchDeliveries): Boolean {
        return oldItem == newItem
    }

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHistoryVH {
        //inflate the view to be used by the payment option view holder
        val binding = SearchItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        private val binding: SearchItemsBinding,
        onItemClick: (position: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }


        fun bind(transaction: SearchDeliveries) {

            with(binding) {
                binding.senderLabel.text = transaction.itemName
                binding.receiptNum.text = transaction.receipt_number
                binding.fromRoute.text = transaction.from_route
                binding.toRoute.text = transaction.to_route
            }
        }

    }
}