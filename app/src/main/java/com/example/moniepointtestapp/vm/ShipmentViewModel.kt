package com.example.moniepointtestapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
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
class ShipmentViewModel @Inject constructor(private val shipmentDetailsRepository: ShipmentDetailsRepository) :
    ViewModel() {
    private var _transactions: MutableLiveData<TFUiState> = MutableLiveData(TFUiState(ShipmentStatus.ALL, emptyList()))
    val transactions: LiveData<TFUiState> = _transactions
    private val _transactionType = MutableStateFlow(ShipmentStatus.ALL)

    init {
        viewModelScope.launch {
            retrieveTransactions()
        }
    }

    private suspend fun retrieveTransactions() {
        combine(
            _transactionType,
            flowOf(shipmentDetailsRepository.getTransactions())
        ) { transactionType, transactions ->

            val filteredTransactions = transactions?.filter {
                when (transactionType) {
                    ShipmentStatus.ALL -> true
                    ShipmentStatus.COMPLETED -> it.status == "Completed"
                    ShipmentStatus.PENDING_ORDER -> it.status == "Pending"
                    ShipmentStatus.CANCELLED -> it.status == "Cancelled"
                    ShipmentStatus.IN_PROGRESS -> it.status == "In-Progress"
                }
            }
            TFUiState(transactionType, filteredTransactions)
        }.collect { transactions ->
            _transactions.value = transactions

        }
    }


     fun retrieveAllShippingCount() = liveData {
         emit(UiState.Loading)
        val result = shipmentDetailsRepository.getTransactions()
         emit(UiState.Success(result))
    }


    fun filterByStatus(status: ShipmentStatus) {
        _transactionType.value = status
    }

}