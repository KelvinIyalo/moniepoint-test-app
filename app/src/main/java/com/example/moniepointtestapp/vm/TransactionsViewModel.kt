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
class TransactionsViewModel @Inject constructor(private val shipmentDetailsRepository: ShipmentDetailsRepository) :
    ViewModel() {
    private var _transactions: MutableLiveData<TFUiState> =
        MutableLiveData(TFUiState(ShipmentStatus.ALL, emptyList()))
    val transactions: LiveData<TFUiState> = _transactions
    private val _transactionType = MutableStateFlow(ShipmentStatus.ALL)

    private val _searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            retrieveTransactions()
        }
    }

    private suspend fun retrieveTransactions() {
        combine(
            _transactionType,
            flowOf(shipmentDetailsRepository.getTransactions()),
            _searchQuery
        ) { transactionType, transactions, searchQuery ->
            when (transactionType) {

                ShipmentStatus.ALL -> {

                    TFUiState(
                        transactionType,

                        transactions?.filter {
                            searchFilterPredicate(searchQuery, it)
                        }
                    )
                }

                ShipmentStatus.COMPLETED -> {
                    TFUiState(
                        transactionType,
                        transactions?.filter { it.status == "Completed" }?.filter {
                            searchFilterPredicate(searchQuery, it)
                        }
                    )

                }

                ShipmentStatus.PENDING_ORDER -> {
                    TFUiState(
                        transactionType,
                        transactions?.filter { it.status == "Pending" }?.filter {
                            searchFilterPredicate(searchQuery, it)
                        }
                    )

                }

                ShipmentStatus.CANCELLED -> {
                    TFUiState(
                        transactionType,
                        transactions?.filter { it.status == "Cancelled" }?.filter {
                            searchFilterPredicate(searchQuery, it)
                        }
                    )

                }

                ShipmentStatus.IN_PROGRESS -> {
                    TFUiState(
                        transactionType,
                        transactions?.filter { it.status == "In-Progress" }?.filter {
                            searchFilterPredicate(searchQuery, it)
                        }
                    )

                }
            }
        }.collect { transactions ->
            _transactions.value = transactions

        }
    }


     fun retrieveAllShippingCount() = liveData {
         emit(UiState.Loading)
        val result = shipmentDetailsRepository.getTransactions()
         emit(UiState.Success(result))
    }


    fun filterByAll() {
        _transactionType.value = ShipmentStatus.ALL
    }

    fun filterByCompleted() {
        _transactionType.value = ShipmentStatus.COMPLETED
    }

    fun filterByPending() {
        _transactionType.value = ShipmentStatus.PENDING_ORDER
    }

    fun filterByInProgress() {
        _transactionType.value = ShipmentStatus.IN_PROGRESS
    }

    fun filterByCancelled() {
        _transactionType.value = ShipmentStatus.CANCELLED
    }

    fun filterBySearchQuery(searchQuery: String) {
        _searchQuery.value = searchQuery
    }

    private fun searchFilterPredicate(searchQuery: String, transaction: ShipmentDetails): Boolean {
        return transaction.status?.contains(searchQuery, true) == true ||
                transaction.receipt_number!!.contains(searchQuery, true) ||
                transaction.amount.toString().contains(searchQuery, true) ||
                transaction.receipt_number?.contains(searchQuery, true) == true
    }

}