package br.com.lbcoutinho.financask.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.lbcoutinho.financask.R
import br.com.lbcoutinho.financask.extension.format
import br.com.lbcoutinho.financask.model.Transaction
import br.com.lbcoutinho.financask.model.TransactionType

class UpdateTransactionDialog(private val context: Context, viewGroup: ViewGroup) :
    TransactionFormDialog(context, viewGroup) {

    override val titlePositiveButton: Int
        get() = R.string.update

    override fun getTitleByType(transactionType: TransactionType): Int {
        return when (transactionType) {
            TransactionType.REVENUE -> R.string.update_revenue
            TransactionType.EXPENSE -> R.string.update_expense
        }
    }

    fun configureDialog(transaction: Transaction, delegate: (transaction: Transaction) -> Unit) {
        val transactionType = transaction.type
        super.configureDialog(transactionType, delegate)
        setFields(transaction, transactionType)
    }

    private fun setFields(transaction: Transaction, transactionType: TransactionType) {
        valueField.setText(transaction.value.toString())
        dateField.setText(transaction.date.format())
        val categoriesArray = context.resources.getStringArray(getCategoriesArray(transactionType))
        categoryField.setSelection(categoriesArray.indexOf(transaction.category))
    }

}