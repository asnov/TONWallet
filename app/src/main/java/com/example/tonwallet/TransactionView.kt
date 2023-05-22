package com.example.tonwallet

import java.time.LocalDateTime


data class TransactionView(
    var id: String,
    var now: Long,
    var date: LocalDateTime,
    var amount: Long,
    var isIncome: Boolean,
    var address: String,
    var fee: Long,
    var description: String,
) {

}