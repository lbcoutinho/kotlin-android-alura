package br.com.lbcoutinho.financask.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.lbcoutinho.financask.R
import br.com.lbcoutinho.financask.model.TransactionType

class AddTransactionDialog(context: Context, viewGroup: ViewGroup) : TransactionFormDialog(context, viewGroup) {

    override val titlePositiveButton: Int
        get() = R.string.add

    override fun getTitleByType(transactionType: TransactionType): Int {
        return when (transactionType) {
            TransactionType.REVENUE -> R.string.add_revenue
            TransactionType.EXPENSE -> R.string.add_expense
        }
    }


}