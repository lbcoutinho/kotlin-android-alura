package br.com.lbcoutinho.financask.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.lbcoutinho.financask.R
import br.com.lbcoutinho.financask.extension.format
import br.com.lbcoutinho.financask.extension.formatAsPtBr
import br.com.lbcoutinho.financask.model.Transaction
import br.com.lbcoutinho.financask.model.TransactionType
import kotlinx.android.synthetic.main.transaction_item.view.transaction_value
import kotlinx.android.synthetic.main.transaction_item.view.transaction_category
import kotlinx.android.synthetic.main.transaction_item.view.transaction_date
import kotlinx.android.synthetic.main.transaction_item.view.transaction_icon

class TransactionListAdapter(
    private val transactions: List<Transaction>,
    private val context: Context
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.transaction_item, parent, false)
        val item = getItem(position)

        if (item.type == TransactionType.REVENUE) {
            view.transaction_value.setTextColor(ContextCompat.getColor(context, R.color.revenue))
            view.transaction_icon.setBackgroundResource(R.drawable.icon_transaction_item_revenue)
        } else {
            view.transaction_value.setTextColor(ContextCompat.getColor(context, R.color.expense))
            view.transaction_icon.setBackgroundResource(R.drawable.icon_transaction_item_expense)
        }

        view.transaction_category.text = item.category
        view.transaction_value.text = item.value.formatAsPtBr()
        view.transaction_date.text = item.date.format()
        return view
    }

    override fun getItem(position: Int): Transaction {
        return transactions[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transactions.size
    }

}