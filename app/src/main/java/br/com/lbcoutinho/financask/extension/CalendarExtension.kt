package br.com.lbcoutinho.financask.extension

import java.text.SimpleDateFormat
import java.util.Calendar

private const val defaultFormat = "dd/MM/yyyy"

fun Calendar.format(format: String = defaultFormat): String {
    return SimpleDateFormat(format).format(this.time)
}