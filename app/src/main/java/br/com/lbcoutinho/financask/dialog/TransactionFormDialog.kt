package br.com.lbcoutinho.financask.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.lbcoutinho.financask.R
import br.com.lbcoutinho.financask.extension.convertToCalendar
import br.com.lbcoutinho.financask.extension.format
import br.com.lbcoutinho.financask.model.Transaction
import br.com.lbcoutinho.financask.model.TransactionType
import kotlinx.android.synthetic.main.transaction_form.view.transaction_form_category
import kotlinx.android.synthetic.main.transaction_form.view.transaction_form_date
import kotlinx.android.synthetic.main.transaction_form.view.transaction_form_value
import java.math.BigDecimal
import java.util.Calendar

abstract class TransactionFormDialog(
    private val context: Context,
    private val viewGroup: ViewGroup
) {

    private val dialog = createDialog()
    protected val valueField = dialog.transaction_form_value
    protected val dateField = dialog.transaction_form_date
    protected val categoryField = dialog.transaction_form_category
    protected abstract val titlePositiveButton: Int

    protected abstract fun getTitleByType(transactionType: TransactionType): Int

    fun configureDialog(transactionType: TransactionType, delegate: (transaction: Transaction) -> Unit) {
        configureDateField()
        configureCategoryField(transactionType)
        showDialog(transactionType, delegate)
    }

    private fun createDialog() = LayoutInflater.from(context).inflate(R.layout.transaction_form, viewGroup, false)

    private fun showDialog(transactionType: TransactionType, delegate: (transaction: Transaction) -> Unit) {
        val title = getTitleByType(transactionType)

        AlertDialog.Builder(context).setTitle(title).setView(dialog)
            .setPositiveButton(titlePositiveButton) { _: DialogInterface, _: Int ->
                val value = try {
                    BigDecimal(valueField.text.toString())
                } catch (e: NumberFormatException) {
                    Toast.makeText(context, "Error on value conversion", Toast.LENGTH_LONG).show()
                    BigDecimal.ZERO
                }

                val date = dateField.text.toString().convertToCalendar()
                val category = categoryField.selectedItem.toString()

                val transaction = Transaction(transactionType, category, value, date)
                delegate(transaction)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun configureCategoryField(transactionType: TransactionType) {
        val categoriesArray = getCategoriesArray(transactionType)

        val categoriesAdapter =
            ArrayAdapter.createFromResource(context, categoriesArray, android.R.layout.simple_spinner_dropdown_item)
        categoryField.adapter = categoriesAdapter
    }

    protected fun getCategoriesArray(transactionType: TransactionType): Int {
        return when (transactionType) {
            TransactionType.REVENUE -> R.array.revenue_categories
            TransactionType.EXPENSE -> R.array.expense_categories
        }
    }

    private fun configureDateField() {
        val today = Calendar.getInstance()

        val startYear = today.get(Calendar.YEAR)
        val startMonth = today.get(Calendar.MONTH)
        val startDay = today.get(Calendar.DAY_OF_MONTH)

        with(dateField) {
            setText(today.format())
            setOnClickListener {
                DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, day)
                    setText(selectedDate.format())
                }, startYear, startMonth, startDay).show()
            }
        }
    }
}