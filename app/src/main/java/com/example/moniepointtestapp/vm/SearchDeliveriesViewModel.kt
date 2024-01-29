package com.example.moniepointtestapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.moniepointtestapp.model.SearchDeliveries
import com.example.moniepointtestapp.model.ShipmentDetails
import com.example.moniepointtestapp.model.ShipmentStatus
import com.example.moniepointtestapp.model.TFUiState
import com.example.moniepointtestapp.repo.ShipmentDetailsRepository
import com.example.moniepointtestapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchDeliveriesViewModel @Inject constructor(private val shipmentDetailsRepository: ShipmentDetailsRepository) :
    ViewModel() {
    private var _transactions: MutableLiveData<TFUiState> =
        MutableLiveData(TFUiState(null, emptyList()))
    val transactions: LiveData<TFUiState> = _transactions

    private val _searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            searchDeliveries()
        }
    }

    private suspend fun searchDeliveries() {
        combine(
            flowOf(shipmentDetailsRepository.searchDeliveries()),
            _searchQuery
        ) { transactions, searchQuery ->

            TFUiState(
                null,
                searchDeliveries = transactions?.filter {
                    searchFilterPredicate(searchQuery, it)
                }
            )

        }.collect { transactions ->
            _transactions.value = transactions

        }
    }

    fun filterBySearchQuery(searchQuery: String) {
        _searchQuery.value = searchQuery
    }

    private fun searchFilterPredicate(searchQuery: String, transaction: SearchDeliveries): Boolean {
        return transaction.status?.contains(searchQuery, true) == true ||
                transaction.itemName.toString().contains(searchQuery, true) ||
                transaction.receipt_number?.contains(searchQuery, true) == true
    }

}