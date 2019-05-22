package br.com.lbcoutinho.financask.dao

import br.com.lbcoutinho.financask.model.Transaction

class TransactionDao {

    companion object {
        private val transactions = mutableListOf<Transaction>()
    }

    fun add(transaction: Transaction) {
        transactions.add(transaction)
    }

    fun update(transaction: Transaction, position: Int) {
        transactions[position] = transaction;
    }

    fun remove(position: Int) {
        transactions.removeAt(position)
    }

    fun getAll() = transactions
}