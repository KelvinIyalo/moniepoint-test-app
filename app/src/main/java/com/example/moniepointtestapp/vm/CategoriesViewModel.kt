package com.example.moniepointtestapp.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moniepointtestapp.model.ShipmentStatus
import com.example.moniepointtestapp.model.TFUiState
import com.example.moniepointtestapp.repo.ShipmentDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val shipmentDetailsRepository: ShipmentDetailsRepository) :
    ViewModel() {
    private var _transactions: MutableLiveData<TFUiState> = MutableLiveData(TFUiState(ShipmentStatus.ALL, emptyList()))
    val transactions: LiveData<TFUiState> = _transactions
    private val _transactionType = MutableStateFlow(ShipmentStatus.ALL)

    private val _searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            retrieveCategories()
        }
    }

    private suspend fun retrieveCategories() {
        combine(
            _transactionType,
            flowOf(shipmentDetailsRepository.getCategories()),
            _searchQuery
        ) { transactionType, transactions, searchQuery ->
            TFUiState(
                transactionType,
                categories = transactions
            )
        }.collect { transactions ->
            _transactions.value = transactions

        }
    }


}