package br.com.lbcoutinho.financask.model

import java.math.BigDecimal
import java.util.Calendar

class Transaction(
    val type: TransactionType,
    val category: String = "Undefined",
    val value: BigDecimal,
    val date: Calendar = Calendar.getInstance()
)