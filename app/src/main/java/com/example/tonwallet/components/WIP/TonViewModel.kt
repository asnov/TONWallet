package com.example.tonwallet.components.WIP

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import org.ton.mnemonic.Mnemonic
import kotlin.coroutines.EmptyCoroutineContext


class TonViewModel : ViewModel() {
    private val userLiveData: MutableLiveData<WalletUiState> = MutableLiveData<WalletUiState>()

    private val _uiState = MutableStateFlow(WalletUiState())
    val uiState: StateFlow<WalletUiState> = _uiState.asStateFlow()

    private val scope = CoroutineScope(Job() + Dispatchers.Main) //  + exceptionHandler ???
    val scopeFailureProtected = CoroutineScope(SupervisorJob())

    var counter = 0
    var mnemonic: List<String> = listOf()

    fun generateSeed() {
        runBlocking {
            mnemonic = Mnemonic.generate()
        }
//        val job = viewModelScope.launch {
//            mnemonic = Mnemonic.generate()
//        }
    }

    fun loadAccount() {
        // Do an asynchronous operation to fetch users.
        val job = viewModelScope.launch(
            context = EmptyCoroutineContext,
        ) {

        }
    }

    fun loadTransactions() {
        // if a task fails, cancel others
        val job = scope.launch {
            withContext(Dispatchers.IO) {

            }
        }
    }

    suspend fun loadTransactions2() {
        // if a task fails, continue with others
        val job = supervisorScope {
            launch {
                withContext(Dispatchers.IO) {
                }
            }
            launch {
                withContext(Dispatchers.IO) {

                }
            }
        }
    }
}