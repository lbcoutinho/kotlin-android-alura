package br.com.lbcoutinho.financask.view

import android.support.v4.content.ContextCompat
import android.view.View
import br.com.lbcoutinho.financask.R
import br.com.lbcoutinho.financask.extension.formatAsPtBr
import br.com.lbcoutinho.financask.model.Transaction
import br.com.lbcoutinho.financask.model.TransactionType
import kotlinx.android.synthetic.main.summary_card.view.summary_card_expense
import kotlinx.android.synthetic.main.summary_card.view.summary_card_revenue
import kotlinx.android.synthetic.main.summary_card.view.summary_card_total
import java.math.BigDecimal

class SummaryView(
    private val view: View,
    private val transactions: List<Transaction>
) {

    private fun sumByType(type: TransactionType): BigDecimal =
        transactions.filter { it.type == type }.sumByDouble { it.value.toDouble() }.toBigDecimal()

    fun updateSummary() {
        val revenue = sumByType(TransactionType.REVENUE)
        val expense = sumByType(TransactionType.EXPENSE)
        val total = revenue.subtract(expense)

        with(view) {
            summary_card_revenue.text = revenue.formatAsPtBr()
            summary_card_expense.text = expense.formatAsPtBr()
            summary_card_total.text = total.formatAsPtBr()

            val color = if (total >= BigDecimal.ZERO) R.color.revenue else R.color.expense
            summary_card_total.setTextColor(ContextCompat.getColor(context, color))
        }
    }
}