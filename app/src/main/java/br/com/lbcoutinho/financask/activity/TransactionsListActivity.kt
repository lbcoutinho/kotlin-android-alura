package br.com.lbcoutinho.financask.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import br.com.lbcoutinho.financask.R
import br.com.lbcoutinho.financask.adapter.TransactionListAdapter
import br.com.lbcoutinho.financask.dao.TransactionDao
import br.com.lbcoutinho.financask.dialog.AddTransactionDialog
import br.com.lbcoutinho.financask.dialog.UpdateTransactionDialog
import br.com.lbcoutinho.financask.model.Transaction
import br.com.lbcoutinho.financask.model.TransactionType
import br.com.lbcoutinho.financask.view.SummaryView
import kotlinx.android.synthetic.main.transactions_list_activity.transactions_list_add_expense
import kotlinx.android.synthetic.main.transactions_list_activity.transactions_list_add_menu
import kotlinx.android.synthetic.main.transactions_list_activity.transactions_list_add_revenue
import kotlinx.android.synthetic.main.transactions_list_activity.transactions_list_listview

class TransactionsListActivity : AppCompatActivity() {

    private val dao = TransactionDao()
    private val transactions = dao.getAll()
    private val activityView by lazy {
        window.decorView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transactions_list_activity)

        bindMenuEvents()

        updateSummary()
        updateList()
    }

    private fun updateViews() {
        transactions.sortBy { it.date.timeInMillis }
        updateSummary()
        updateList()
    }

    private fun updateSummary() {
        SummaryView(activityView, transactions).updateSummary()
    }

    private fun updateList() {
        with(transactions_list_listview) {
            adapter = TransactionListAdapter(transactions, this@TransactionsListActivity)
            onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val transaction = transactions[position]
                showUpdateTransactionDialog(position, transaction)
            }
            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == 1) {
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val position = adapterMenuInfo.position
            dao.remove(position)
            updateViews()
        }
        return super.onContextItemSelected(item)
    }

    private fun bindMenuEvents() {
        transactions_list_add_revenue.setOnClickListener {
            showAddTransactionDialog(TransactionType.REVENUE)
        }

        transactions_list_add_expense.setOnClickListener {
            showAddTransactionDialog(TransactionType.EXPENSE)
        }
    }

    private fun showAddTransactionDialog(transactionType: TransactionType) {
        AddTransactionDialog(this, activityView as ViewGroup).configureDialog(transactionType) { transaction ->
            dao.add(transaction)
            updateViews()
            transactions_list_add_menu.close(true)
        }
    }

    private fun showUpdateTransactionDialog(position: Int, transaction: Transaction) {
        UpdateTransactionDialog(this, activityView as ViewGroup).configureDialog(transaction) { updatedTransaction ->
            dao.update(updatedTransaction, position)
            updateViews()
        }
    }

}