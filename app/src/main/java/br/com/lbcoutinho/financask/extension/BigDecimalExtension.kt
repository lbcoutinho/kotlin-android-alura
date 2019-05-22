package br.com.lbcoutinho.financask.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.Locale

fun BigDecimal.formatAsPtBr(): String {
    val decimalFormatter = DecimalFormat.getCurrencyInstance(Locale("pt", "br"))
    return decimalFormatter.format(this).replace("R$", "R$ ").replace("-R$ ", "R$ -")
}