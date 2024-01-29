package com.example.moniepointtestapp.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moniepointtestapp.R
import com.example.moniepointtestapp.databinding.CategoryTypeBinding
import com.example.moniepointtestapp.databinding.ShipmentHistoryItemBinding
import com.example.moniepointtestapp.model.Categories
import com.example.moniepointtestapp.model.ShipmentDetails


class CategoriesAdapter(
    private val onItemClicked: (position: Int, itemAtPosition: Categories, binding: CategoryTypeBinding) -> Unit
) : ListAdapter<Categories, CategoriesAdapter.TransactionHistoryVH>(object :
    DiffUtil.ItemCallback<Categories>() {

    override fun areItemsTheSame(oldItem: Categories, newItem: Categories): Boolean {
        return oldItem.itemName == newItem.itemName
    }

    override fun areContentsTheSame(oldItem: Categories, newItem: Categories): Boolean {
        return oldItem == newItem
    }

}) {
    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHistoryVH {
        //inflate the view to be used by the payment option view holder
        val binding =
            CategoryTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionHistoryVH(binding, onItemClick = { position ->
            val itemAtPosition = currentList[position]
            this.onItemClicked(position, itemAtPosition, binding)
        })

    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: TransactionHistoryVH, position: Int) {
        val itemAtPosition = currentList[position]
        holder.bind(itemAtPosition)
    }


    inner class TransactionHistoryVH(
        private val binding: CategoryTypeBinding,
        onItemClick: (position: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }


        fun bind(transaction: Categories) {

            with(binding) {
                itemName.text = transaction.itemName
            }
        }

    }

}